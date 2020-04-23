package xyz.taka8rie.finalback.controller;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.json.JsonObjectDecoder;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;
import xyz.taka8rie.finalback.Service.KanfangService;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.dao.HouseDAO;
import xyz.taka8rie.finalback.dao.KanfangDAO;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.pojo.Kanfang;
import xyz.taka8rie.finalback.pojo.User;
import xyz.taka8rie.finalback.result.Result;
import xyz.taka8rie.finalback.result.ResultFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class KanfangController {
    @Autowired
    KanfangService kanfangService;
    @Autowired
    UserService userService;
    @Autowired
    KanfangDAO kanfangDAO;
    @Autowired
    HouseDAO houseDAO;


    @CrossOrigin
    @PostMapping("api/kanfangadd")
    public Result add(@RequestBody String info) {
        Kanfang kanfang = JSONObject.parseObject(info, Kanfang.class);
//        System.out.println("进入看房controller");
//        System.out.println("看房的时间是: " + kanfang.getSeeTime());
        System.out.println("添加看房请求的房屋账号: "+kanfang.getHouseNumber());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        System.out.println("当前用户的id是: "+user.getId());
        if (kanfangService.listRepeatKanfang(kanfang.getHouseNumber(), user.getId()) != null) {
            System.out.println("说明scoreinfo表中已经有了预约看房的记录了");
            return ResultFactory.buildFailResult("已经添加过该预约订单了");
        }

        kanfang.setTenantNumber(user.getId());
        kanfangService.addKanfang(kanfang);
        return ResultFactory.buildSuccessResult("添加成功");
    }

    //后台租客查看自己的预约订单
    @CrossOrigin
    @GetMapping("api/myyuyues")
    public List<Kanfang> zukeYuyue() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        return kanfangService.listByTenantNumberToKanFang(user.getId());
    }

    //后台管理员查看所有预约订单
    @CrossOrigin
    @GetMapping("api/allyuyues")
    public List<Kanfang> allYuyue() {
        System.out.println("进入admin查看所有预约订单的方法");
        return kanfangService.listAllyuyue();
    }




    //后台删除预约订单
    @CrossOrigin
    @PostMapping("api/deleteyuyue")
    public void deleteYuyue(@RequestBody Kanfang kanfang) {
        kanfangService.deleteByShowNumber(kanfang);
    }

    //后台修改预约订单
    @CrossOrigin
    @PostMapping("api/edityuyue")
    public Kanfang editYuyue(@RequestBody Kanfang kanfang) {
        String username=SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        System.out.println("租客的id是 "+user.getId());
        System.out.println("预约看房的房屋的编号是 "+kanfang.getHouseNumber());
        kanfang.setTenantNumber(user.getId());
        kanfangService.addKanfang(kanfang);
        return kanfang;
    }

    //余弦推荐算法
    @CrossOrigin
    @PostMapping("api/recommendhouse")
    public List<House> SimilarDegreeByCos() {
        String username=SecurityUtils.getSubject().getPrincipal().toString();
        if (username == null) {
            System.out.println("在余弦推荐算法中获取不到目前登录的账户名,查看是否登录");
        }
        User user = userService.findByUsername(username);
        ArrayList<Kanfang> currentUser = (ArrayList<Kanfang>) kanfangService.listByTenantNumberToKanFang(user.getId());
        if (currentUser.isEmpty()) {
            System.out.println("未添加预约看房申请,无法进行推荐");
            return  null;
        }
        ArrayList<User> allUser= (ArrayList<User>) userService.findAllUser();
        ArrayList<Integer> allUserId= (ArrayList<Integer>) allUser.stream().map(User::getId).collect(Collectors.toList());
        //第一个参数是用户ID,第二个参数是该用户所预约看房的房屋号码的arrayList.
        Map<Integer,ArrayList<Integer>> userScoreTable=new HashMap<>();

        ArrayList<Kanfang> everyUserHouse=new ArrayList<>();
        for (int i = 0; i < allUserId.size(); i++) {
            //第一个参数是各个用户的ID,第二个是查找对应用户所找到的房屋
            userScoreTable.put(allUserId.get(i), kanfangDAO.allHouseNumberDAO(allUserId.get(i)));
        }
        System.out.println(userScoreTable);

        //建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        int [][]sparseMatrix=new int[allUserId.size()][allUserId.size()];
        Map<Integer,Integer> userItemLength=new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<Integer, Set<Integer>> itemUserCollection=new HashMap<>();//建立物品到用户的倒排表 eg: a A B

        Set<Integer> items = new HashSet<>();//辅助存储物品集合
        Map<Integer, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, Integer> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射


        System.out.println("allUserId.size "+allUserId.size());

        for (int i = 0; i < allUserId.size(); i++) {//依次处理logininfo表中的所以用户
            List<Integer> tempList=userScoreTable.get(allUserId.get(i));
            System.out.println("allUserId.get(i) is "+allUserId.get(i));
            System.out.println("tempList is "+tempList);
            System.out.println("当前最外层循环i "+i);

            Integer[] user_item= tempList.toArray(new Integer[tempList.size()]);
            int length=user_item.length;
            System.out.println("内层循环length是 "+length);
            userItemLength.put(allUserId.get(i), length);

            System.out.println("userItemLength is "+userItemLength);
            userID.put(allUserId.get(i), i);//用户ID与稀疏矩阵建立对应关系, id --房屋
            idUser.put(i, allUserId.get(i));//互相存储序号, 用户ID跟矩阵里边的行列号对应

            System.out.println("userID is "+userID);

            for (int j = 0; j < length; j++) {
                System.out.println("当前的j "+j);
                //因为user_item的长度只有1,所以从0开始算时,j=1就溢出了
                System.out.println("j is "+j+" user_item[j]是 "+user_item[j]);
                if (items.contains(user_item[j])) {//如果已经包含对应的物品--用户映射，直接添加对应的用户
                    itemUserCollection.get(user_item[j]).add(allUserId.get(i));
                    System.out.println("if里的itemUserCollection "+itemUserCollection);
                }else {
                    System.out.println("进入else语句");
                    items.add(user_item[j]);
                    System.out.println("user_item[j] "+user_item[j]);
                    System.out.println("items is "+items);
                    itemUserCollection.put(user_item[j], new HashSet<Integer>());//创建物品--用户倒排关系
                    System.out.println("itemUserCollection.put时的collection "+itemUserCollection);
                    itemUserCollection.get(user_item[j]).add(allUserId.get(i));
                    System.out.println("itemUserCollection.get时的collection "+itemUserCollection);
                }
            }
            System.out.println("经过循环后的items是 "+items);
        }
        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】 !!!此处出现错误
        Set<Map.Entry<Integer,Set<Integer>>> entrySet=itemUserCollection.entrySet();
        System.out.println("entrySet is "+entrySet);
        Iterator<Map.Entry<Integer, Set<Integer>>> iterator = entrySet.iterator();
        System.out.println("iterator is "+iterator);
        while (iterator.hasNext()) {

            Set<Integer> commonUsers = iterator.next().getValue();
            for (Integer user_u : commonUsers) {
                for (Integer user_v : commonUsers) {
                    if (user_u.equals(user_v)) {
                        continue;
                    }
                    //这里的范围溢出,检查说是10,已解决
                   System.out.println("userID.get(u) "+userID.get(user_u)+" userID.get(v) "+userID.get(user_v));
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)]+=1;//计算用户u与用户v都有正反馈的物品总数

                }
            }
        }
        //计算用户之间的相似度【余弦相似性】  先设置为当前登录的用户ID
        int recommendUserId=userID.get(user.getId());
        System.out.println("对应的recommendUserId is "+recommendUserId);
        System.out.println("sparseMatrix.length is "+sparseMatrix.length);
        System.out.println("--------------------------------------------");
        double  maxDegree=0.000000;
        double tempDegree=maxDegree;
        int Jlast=0;//给Jlast赋一个值,确保不出现错误。
        for (int j = 0; j < sparseMatrix.length; j++) {
            if (j != recommendUserId) {
//                System.out.println("idUser.get(recommendUserId) "+idUser.get(recommendUserId));
//                System.out.println("idUser.get(j) "+idUser.get(j));
                tempDegree=sparseMatrix[recommendUserId][j] / Math.sqrt(userItemLength.get(idUser.get(recommendUserId)) * userItemLength.get(idUser.get(j)));
                if (tempDegree > maxDegree) {
                    maxDegree=tempDegree;
                    Jlast=j;
                }
                System.out.println("当前登录的用户ID(要推荐的ID) " + recommendUserId + " 和第J个ID " + j + " 相似度 " + tempDegree);
            }
        }
        System.out.println("跟当前用户最像的Jlast是 "+Jlast+" 其对应的用户ID是 "+idUser.get(Jlast));

        ArrayList houseNumberList = kanfangDAO.allHouseNumberDAO(idUser.get(Jlast));
        return houseDAO.findAllByHouseNumberIn(houseNumberList);

//        return  null;
//        System.out.println("------------计算推荐度----------");
//        //计算指定用户recommendUser的物品推荐度
//        for (Integer item : items) {
//            Set<Integer> users =itemUserCollection.get(item);//得到购买当前物品的所有用户集合,需要将真正的用户ID转换成矩阵里边的行列号.
//            ArrayList arrayList=new ArrayList(users);
//
//            for(int i=0;i<arrayList.size();i++){
//                arrayList.set(i, i);
//            }
//            if (!users.contains(user.getId())) {//如果当前登录的用户没有购买当前物品，则进行推荐度计算
//                double itemRecommendDegree = 0.0;
//                for (Integer user1 : users) {
//                    //推荐度计算
//
//                    itemRecommendDegree += sparseMatrix[userID.get(user.getId())][user1] / Math.sqrt(userItemLength.get(recommendUserId) * userItemLength.get(user1));
//                }
//                System.out.println("The item "+item+" for "+recommendUserId +"'s recommended degree:"+itemRecommendDegree);
//            }
//        }




    }

}
