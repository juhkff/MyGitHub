package tools;

import com.google.gson.Gson;
import connection.Conn;
import connection.MyConnection;
import model.contact.Contact;
import model.contact.FullContact;
import model.contact.Sort;
import model.property.User;
import test.Client.Request;
import test.Client.RequestProperty;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static wrapper.StaticVariable.URL_ADDRESS;

public class Online {
    public static void main(String[] args){
        System.out.println(isOnline("7999375901"));
    }
    //确认用户上线状态
    public static boolean isOnline(String userID) {
        boolean isOnline = false;
        try {
            Connection connection = Conn.getConnection();
            String sql = "SELECT isOnline FROM userinfo WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isOnline = resultSet.getBoolean("isOnline");
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.Close();
        }
        return isOnline;
    }

    //辅助方法，获得用户已添加的好友ID列表
    public static final ResultSet getFriendIDResultSet(String userID, Connection connection) {
        ResultSet resultSet = null;
        try {
            //MyConnection connection=Conn.getConnection();
            String sql = "SELECT ID FROM user_" + userID + "_contactlist WHERE types=0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in Online.getFriendIDList !");
        } finally {
//            Conn.Close();
        }
        return resultSet;
    }

    //辅助方法，获得用户已添加的群ID列表
    public static final ResultSet getGroupIDResultSet(String userID, Connection connection) {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT ID FROM user_" + userID + "_contactlist WHERE types=1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in Online.getGroupIDResultSet() !");
        } finally {
            //Conn.Close();
        }
        return resultSet;
    }

    //辅助方法，获得用户的外网消息地址
    public static final String getMessageAddressByID(String userID) {
        String result = "error in tools.Online.getMessageAddressByID";
        try {
            Connection connection = Conn.getConnection();
            String sql = "SELECT remoteAddress FROM userinfo WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("remoteAddress");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            Conn.Close();
        }
    }

    //查找并返回数据库中存储的用户的本地局域网地址
    public static final String getLocalAddress(String userID) throws SQLException {
        String result = "";
        do {
            Connection connection = Conn.getConnection();
            String sql = "SELECT localAddress FROM userinfo WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            int num = 0;
            while (resultSet.next()) {
                num++;
                if (num != 1)
                    throw new SQLException("查询出错!");
                result = resultSet.getString("localAddress");
            }
        } while (result.equals(""));
        Conn.Close();
        return result;
    }

    //添加好友而非群
    public static Map<String, Contact> getAddList(String theuserID) throws SQLException {
        Map<String, Contact> userList = new HashMap<String, Contact>();
        Connection connection = Conn.getConnection();
        String sql = "SELECT userinfo.userID,userinfo.nickName,userinfo.headIcon,userinfo.intro,userinfo.isMale,userinfo.isOnline FROM userinfo LEFT JOIN user_" + theuserID + "_contactlist ON userinfo.userID=user_" + theuserID + "_contactlist.ID  WHERE user_" + theuserID + "_contactlist.ID IS NULL AND userinfo.userID!=\'" + theuserID + "\'";
//        String sql="SELECT userID,nickName,headIcon,intro,isMale,isOnline FROM userinfo WHERE userID NOT IN ("+theuserID+")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        String userID;
        String nickName;
        byte[] headIcon = null;
        String intro = null;
        Boolean isMale;
        Boolean isOnline;

        while (resultSet.next()) {
            userID = resultSet.getString("userID");
            nickName = resultSet.getString("nickName");
            InputStream inputStream = null;
            inputStream = resultSet.getBinaryStream("headIcon");
            if (inputStream == null)
                headIcon = null;
            else {
                try {
                    headIcon = new byte[inputStream.available()];
                    inputStream.read(headIcon, 0, inputStream.available());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("error in Online.getAddList()!");
                }
            }
            intro = resultSet.getString("intro");
            isMale = resultSet.getBoolean("isMale");
            isOnline = resultSet.getBoolean("isOnline");
            Contact contact = new Contact(userID, nickName, headIcon, intro, isMale, (byte) 0, isOnline);
            userList.put(userID, contact);
            //userList.put(resultSet.getString("userID"), resultSet.getString("nickName"));
        }
        Conn.Close();
        return userList;
    }

    public static Map<String, Contact> searchFriendList(String theuserID, String searchID) throws SQLException {
        Map<String, Contact> userList = new HashMap<String, Contact>();
        Connection connection = Conn.getConnection();
        String sql = "SELECT userinfo.userID,userinfo.nickName,userinfo.headIcon,userinfo.intro,userinfo.isMale,userinfo.isOnline FROM userinfo LEFT JOIN user_" + theuserID + "_contactlist ON userinfo.userID=user_" + theuserID + "_contactlist.ID  WHERE user_" + theuserID + "_contactlist.ID IS NULL AND userinfo.userID!=\'" + theuserID + "\' AND userID LIKE \'%" + searchID + "%\'";
//        String sql="SELECT userID,nickName,headIcon,intro,isMale,isOnline FROM userinfo WHERE userID NOT IN ("+theuserID+")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        String userID;
        String nickName;
        byte[] headIcon = null;
        String intro = null;
        Boolean isMale;
        Boolean isOnline;

        while (resultSet.next()) {
            userID = resultSet.getString("userID");
            nickName = resultSet.getString("nickName");
            InputStream inputStream = null;
            inputStream = resultSet.getBinaryStream("headIcon");
            if (inputStream == null)
                headIcon = null;
            else {
                try {
                    headIcon = new byte[inputStream.available()];
                    inputStream.read(headIcon, 0, inputStream.available());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("error in Online.getAddList()!");
                }
            }
            intro = resultSet.getString("intro");
            isMale = resultSet.getBoolean("isMale");
            isOnline = resultSet.getBoolean("isOnline");
            Contact contact = new Contact(userID, nickName, headIcon, intro, isMale, (byte) 0, isOnline);
            userList.put(userID, contact);
            //userList.put(resultSet.getString("userID"), resultSet.getString("nickName"));
        }
        Conn.Close();
        return userList;
    }


    public final static String sendRequest(String userID, String nickName, String receiverID) {
        String result = "";
        Connection connection = Conn.getConnection();
        String sql = "SELECT anotherID,property FROM user_" + receiverID + "_noticelist WHERE anotherID=? AND property=0";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                if (i > 1)
                    throw new SQLException("数据重复!Online.sendRequest");
                i++;
            }
            if (i != 0) {
                result = "CF";        //重复
            } else {
                sql = "INSERT INTO user_" + receiverID + "_noticelist ( anotherID , nickName , property ) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, nickName);
                if (userID.length() == 10)
                    preparedStatement.setInt(3, 0);
                else if (userID.length() == 6)
                    preparedStatement.setInt(3, 1);
                else
                    throw new IOException("ID长度不对!Online.sendRequest");
                int j = preparedStatement.executeUpdate();
                if (j == 1)
                    result = "CG";    //成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Conn.Close();
        }
        return result;              //result:CF/CG
    }


    /**
     * 同意好友邀请
     **/
    public final static int sendAgreeResponse(String userID, String nickName, String receiverID) throws SQLException {
        Connection connection = Conn.getConnection();
        String sql = "INSERT INTO user_" + receiverID + "_noticelist ( anotherID , nickName , property ) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userID);
        preparedStatement.setString(2, nickName);
        preparedStatement.setInt(3, 2);                      /**2:同意好友邀请**/
        int result = preparedStatement.executeUpdate();

        Conn.Close();
        return result;
    }


    /**
     * 根据userID找出用户的方法
     **/
    public static final User findUserByUserID(String userID) throws SQLException, IOException {
        Connection connection = Conn.getConnection();
        String sql = "SELECT * FROM userinfo WHERE userID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userID);

        ResultSet resultSet = preparedStatement.executeQuery();
        int i = 0;
        User user = null;
        while (resultSet.next()) {
            i++;
            //String userID=resultSet.getString("userID");
            String nickName = resultSet.getString("nickName");
            InputStream inputStream;
            inputStream=resultSet.getBinaryStream("headIcon");
            byte[] bytes=null;
            if(inputStream!=null){
                bytes=new byte[inputStream.available()];
                inputStream.read(bytes,0,inputStream.available());
            }
            boolean isMale = resultSet.getBoolean("isMale");
            String birthday = resultSet.getString("birthday");
            String email = resultSet.getString("email");
            String phoneNUm = resultSet.getString("phoneNum");
            String exitTime = resultSet.getString("exitTime");
            user = new User(userID, nickName, bytes,isMale, birthday, email, phoneNUm, exitTime);
        }
        Conn.Close();
        return user;
    }


    /*public static void main(String[] args) throws IOException, SQLException {
        String userID = "7999375901";
        String ID = "6416977175";
        String nickName = "juhkdf";
        bothAddFriend(userID, ID, nickName);
    }*/

    /**
     * 添加好友
     **/
    public final static void bothAddFriend(String userID, String ID, String nickName) throws SQLException, IOException {
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
//        Connection connection = Conn.getConnection();
        String sql = "SELECT headIcon,nickName,isOnline FROM userinfo WHERE userID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        InputStream Agree_inputStream = null;
        String user_Name = null;
        while (resultSet.next()) {
            Agree_inputStream = resultSet.getBinaryStream("headIcon");
            user_Name = resultSet.getString("nickName");
        }
        InputStream Agreed_inputStream = null;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID);
        resultSet = preparedStatement.executeQuery();
        boolean isOnline = false;
        while (resultSet.next()) {
            Agreed_inputStream = resultSet.getBinaryStream("headIcon");
            isOnline = resultSet.getInt("isOnline") != 0;
        }

        sql = "INSERT INTO user_" + userID + "_contactlist(ID,nickName,headIcon,types,status, username ,isupdate) VALUES (?,?,?,?,?,?,1)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID);
        preparedStatement.setString(2, nickName);
        if (Agreed_inputStream != null)
            preparedStatement.setBinaryStream(3, Agreed_inputStream);
        else
            preparedStatement.setBinaryStream(3, null);
        preparedStatement.setInt(4, 0);
        preparedStatement.setBoolean(5, isOnline);
        preparedStatement.setString(6, nickName);
        int i = preparedStatement.executeUpdate();

        sql = "INSERT INTO user_" + ID + "_contactlist(ID,nickName,headIcon,types,status,username,isupdate) VALUES (?,?,?,?,?,?,1)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
        preparedStatement1.setString(1, userID);
        preparedStatement1.setString(2, user_Name);
        if (Agree_inputStream != null)
            preparedStatement1.setBinaryStream(3, Agree_inputStream);
        else
            preparedStatement1.setBinaryStream(3, null);
        preparedStatement1.setInt(4, 0);
        preparedStatement1.setBoolean(5, true);
        preparedStatement1.setString(6, user_Name);
        int j = preparedStatement1.executeUpdate();
        myConnection.Close();
    }

    public static String commitUserInfo(User user, String nothing) {
        String result = null;
        InputStream inputStream = null;
        inputStream = new ByteArrayInputStream(user.getHeadIcon());
        try {
            Connection connection = Conn.getConnection();
            String sql = "UPDATE userinfo SET nickName=?,passWord=?,headIcon=?,isMale=?,birthday=?,email=?,phoneNum=?,intro=? WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getNickName());
            preparedStatement.setString(2, user.getPassWord());
//            InputStream inputStream=null;
//            inputStream=new ByteArrayInputStream(user.getHeadIcon());
            preparedStatement.setBinaryStream(3, inputStream);
            preparedStatement.setBoolean(4, user.isMale());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(user.getBirthday()));
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPhoneNum());
            preparedStatement.setString(8, user.getIntro());
            preparedStatement.setString(9, user.getUserID());
            int commitResult = preparedStatement.executeUpdate();

            ResultSet friendIDResultSetresultSet = Online.getFriendIDResultSet(user.getUserID(), connection);
            while (friendIDResultSetresultSet.next()) {
                String ID = friendIDResultSetresultSet.getString("ID");
                String sql1 = "UPDATE user_" + ID + "_contactlist SET nickName=?,headIcon=?,isupdate=1 WHERE ID=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setString(1, user.getNickName());
//                InputStream inputStream1=null;
//                inputStream1 = new ByteArrayInputStream(user.getHeadIcon());
                preparedStatement1.setBinaryStream(2, inputStream);
                preparedStatement1.setString(3, user.getUserID());
                int commitResult1 = preparedStatement1.executeUpdate();
            }
            ResultSet groupIDResultSet = Online.getGroupIDResultSet(user.getUserID(), connection);


            while (groupIDResultSet.next()) {
                String ID = groupIDResultSet.getString("ID");
                String sql2 = "UPDATE group_" + ID + "_member SET userName=?,userHeadIcon=?,isUpdate=1 WHERE userID=?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setString(1, user.getNickName());
                preparedStatement2.setBinaryStream(2, inputStream);
                preparedStatement2.setString(3, user.getUserID());
                int commitResult2 = preparedStatement2.executeUpdate();
            }
            result = "success";
        } catch (SQLException e) {
            e.printStackTrace();
            result = "error in Tools.Online.commitUserInfo";
        } finally {
            Conn.Close();
        }
        return result;
    }

    public static String commitUserInfo(User user) {
        String result = null;
        InputStream inputStream = null;
        inputStream = new ByteArrayInputStream(user.getHeadIcon());
        try {
            Connection connection = Conn.getConnection();
            String sql = "UPDATE userinfo SET nickName=?,headIcon=?,intro=? WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getNickName());
//            InputStream inputStream=null;
//            inputStream=new ByteArrayInputStream(user.getHeadIcon());
            preparedStatement.setBinaryStream(2, inputStream);
//            preparedStatement.setBoolean(4,user.isMale());
//            preparedStatement.setTimestamp(5, Timestamp.valueOf(user.getBirthday()));
//            preparedStatement.setString(6,user.getEmail());
//            preparedStatement.setString(7,user.getPhoneNum());
            preparedStatement.setString(3, user.getIntro());
            preparedStatement.setString(4, user.getUserID());
            int commitResult = preparedStatement.executeUpdate();

            ResultSet friendIDResultSetresultSet = Online.getFriendIDResultSet(user.getUserID(), connection);
            while (friendIDResultSetresultSet.next()) {
                String ID = friendIDResultSetresultSet.getString("ID");
                String sql1 = "UPDATE user_" + ID + "_contactlist SET nickName=?,headIcon=?,isupdate=1 WHERE ID=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setString(1, user.getNickName());
//                InputStream inputStream1=null;
//                inputStream1 = new ByteArrayInputStream(user.getHeadIcon());
                preparedStatement1.setBinaryStream(2, inputStream);
                preparedStatement1.setString(3, user.getUserID());
                int commitResult1 = preparedStatement1.executeUpdate();
            }
            ResultSet groupIDResultSet = Online.getGroupIDResultSet(user.getUserID(), connection);


            while (groupIDResultSet.next()) {
                String ID = groupIDResultSet.getString("ID");
                String sql2 = "UPDATE group_" + ID + "_member SET userName=?,userHeadIcon=?,isUpdate=1 WHERE userID=?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setString(1, user.getNickName());
                preparedStatement2.setBinaryStream(2, inputStream);
                preparedStatement2.setString(3, user.getUserID());
                int commitResult2 = preparedStatement2.executeUpdate();
            }
            result = "success";
        } catch (SQLException e) {
            e.printStackTrace();
            result = "error in Tools.Online.commitUserInfo";
        } finally {
            Conn.Close();
        }
        return result;
    }

    public static boolean checkPhone(String userID, String phoneNum) {
        boolean result = false;
        try {
            String searchedPhoneNum = null;
            Connection connection = Conn.getConnection();
            String sql = "SELECT phoneNum FROM userinfo WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                searchedPhoneNum = resultSet.getString("phoneNum");
                break;
            }
            if (searchedPhoneNum == null || !searchedPhoneNum.equals(phoneNum))
                result = false;
            else if (searchedPhoneNum.equals(phoneNum))
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in Online.checkPhone() when connection.prepareStatement(sql) !");
        } finally {
            Conn.Close();
        }
        return result;
    }

    public static void changeContact(String userID, String changedID, String user_name, String sort) {
        try {
            Connection connection = Conn.getConnection();
            String sql = "UPDATE user_" + userID + "_contactlist SET sort=?,username=?,isupdate=1 WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sort);
            preparedStatement.setString(2, user_name);
            preparedStatement.setString(3, changedID);
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.Close();
        }
    }

    public static ArrayList<FullContact> getFriendList(String userID) {
        ArrayList<FullContact> friends = new ArrayList<FullContact>();

        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        String sql = "SELECT ID,nickName,headIcon,status,sort,username FROM user_" + userID + "_contactlist WHERE types=0";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
                String ID = resultSet.getString("ID");
                String nickName = resultSet.getString("nickName");
                InputStream inputStream = null;
                inputStream = resultSet.getBinaryStream("headIcon");
                byte[] headIcon = null;
                if (inputStream != null) {
                    headIcon = new byte[inputStream.available()];
                    inputStream.read(headIcon, 0, inputStream.available());
                    inputStream.close();
                }
                int status = resultSet.getInt("status");
                String sort = resultSet.getString("sort");
                String username = resultSet.getString("username");
                FullContact fullContact = new FullContact(nickName, headIcon, status, sort, ID, username);
                friends.add(fullContact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //所有好友的列表
        MyConnection myConnection1 = new MyConnection();
        Connection connection1 = myConnection1.getConnection();
//        Connection connection1=Conn.getConnection();
        String sql1 = "SELECT userID,intro FROM userinfo where userID IN (SELECT ID FROM user_" + userID + "_contactlist WHERE types=0)";
        try {
            PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                String theuserID = resultSet.getString("userID");
                String intro = resultSet.getString("intro");
                for (FullContact fullContact : friends) {
                    if (fullContact.getId().equals(theuserID)) {
                        fullContact.setMotto(intro);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection1.Close();
        }
        return friends;
    }

    /*public static ArrayList<Sort> getFriendSort(String userID) throws SQLException {
        ArrayList<Sort> sorts = new ArrayList<Sort>();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        String sql = "SELECT sort FROM user_" + userID + "_contactlist WHERE types=0 GROUP BY sort";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thesort = resultSet.getString("sort");
            Sort sort = new Sort(thesort);
            sorts.add(sort);
        }
        return sorts;
    }*/


    public static ArrayList<Sort> getFriendSort(String userID) throws SQLException {
        ArrayList<Sort> sorts = new ArrayList<Sort>();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        String sql = "SELECT sortName FROM user_" + userID + "_Sort WHERE property=0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String thesort = resultSet.getString("sortName");
            Sort sort = new Sort(thesort);
            sorts.add(sort);
        }
        return sorts;
    }

    public static ArrayList<FullContact> getGroupList(String userID) {
        ArrayList<FullContact> fullContacts = new ArrayList<FullContact>();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
//        Connection connection=Conn.getConnection();
        String sql = "SELECT ID,nickName,headIcon,status,sort,username FROM user_" + userID + "_contactlist WHERE types=1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
                String ID = resultSet.getString("ID");
                String nickName = resultSet.getString("nickName");
                InputStream inputStream = null;
                inputStream = resultSet.getBinaryStream("headIcon");
                byte[] headIcon = null;
                if (inputStream != null) {
                    headIcon = new byte[inputStream.available()];
                    inputStream.read(headIcon, 0, inputStream.available());
                    inputStream.close();
                }
                int status = resultSet.getInt("status");
                String sort = resultSet.getString("sort");
                String username = resultSet.getString("username");
                FullContact fullContact = new FullContact(nickName, headIcon, status, sort, ID, username);
                fullContacts.add(fullContact);
            }

            sql = "UPDATE user_" + userID + "_contactlist SET isupdate=0 WHERE isupdate=1 AND types=1";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

            sql = "SELECT groupID,groupIntro FROM groups WHERE groupID IN (SELECT ID FROM user_" + userID + "_contactlist WHERE types=1)";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String groupID = resultSet.getString("groupID");
                String groupIntro = resultSet.getString("groupIntro");
                for (FullContact fullContact : fullContacts) {
                    if (fullContact.getId().equals(groupID)) {
                        fullContact.setMotto(groupIntro);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            myConnection.Close();
        }
        return fullContacts;
    }

    public static ArrayList<Sort> getGroupSortJson(String userID) {
        ArrayList<Sort> sorts = new ArrayList<Sort>();
        MyConnection myConnection = new MyConnection();
        Connection connection = myConnection.getConnection();
        String sql = "SELECT sortName FROM user_" + userID + "_Sort WHERE property=1";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String sort = resultSet.getString("sortName");
                Sort groupSort = new Sort(sort);
                sorts.add(groupSort);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sorts;
    }

    public static void insertFriendSort(String userID,String sortName){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("userID",userID);
        parameters.put("sortName",sortName);
        Request request=new Request(URL_ADDRESS+"/insertSort",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
    }

    public static void createNewSort(String userID, String sortName) throws SQLException {
        MyConnection myConnection=new MyConnection();
        Connection connection1=myConnection.getConnection();
        String sql="INSERT INTO user_"+userID+"_Sort(sortName,property) VALUES (?,0)";
        PreparedStatement preparedStatement=connection1.prepareStatement(sql);
        preparedStatement.setString(1,sortName);
        preparedStatement.execute();
        myConnection.Close();
    }

    public static void createNewGroupSort(String userID,String sortName) throws SQLException {
        MyConnection myConnection=new MyConnection();
        Connection connection1=myConnection.getConnection();
        String sql="INSERT INTO user_"+userID+"_Sort(sortName,property) VALUES (?,1)";
        PreparedStatement preparedStatement=connection1.prepareStatement(sql);
        preparedStatement.setString(1,sortName);
        preparedStatement.execute();
        myConnection.Close();
    }

    public static void updateSort(String userID,String oldName,String newName) throws SQLException {
        MyConnection myConnection=new MyConnection();
        Connection connection1=myConnection.getConnection();
        String sql="UPDATE user_"+userID+"_Sort SET sortName=? WHERE sortName=?";
        PreparedStatement preparedStatement=connection1.prepareStatement(sql);
        preparedStatement.setString(1,newName);
        preparedStatement.setString(2,oldName);
        preparedStatement.execute();
        myConnection.Close();

        MyConnection myConnection1=new MyConnection();
        Connection connection=myConnection1.getConnection();
        String sql1="UPDATE user_"+userID+"_contactlist SET sort=? WHERE sort=?";
        PreparedStatement preparedStatement1=connection.prepareStatement(sql1);
        preparedStatement.setString(1,newName);
        preparedStatement.setString(2,oldName);
        preparedStatement.execute();
        myConnection1.Close();
    }

    public static void updateContactSort(String userID,String ID,String newSortName) throws SQLException {
        MyConnection myConnection=new MyConnection();
        Connection connection1=myConnection.getConnection();
        String sql="UPDATE user_"+userID+"_contactlist SET sort=? WHERE ID=?";
        PreparedStatement preparedStatement=connection1.prepareStatement(sql);
        preparedStatement.setString(1,newSortName);
        preparedStatement.setString(2,ID);
        preparedStatement.execute();
        myConnection.Close();
    }

    public static void updateContactBZ(String userID,String ID,String newBZ) throws SQLException {
        MyConnection myConnection=new MyConnection();
        Connection connection1=myConnection.getConnection();
        String sql="UPDATE user_"+userID+"_contactlist SET username=? WHERE ID=?";
        PreparedStatement preparedStatement=connection1.prepareStatement(sql);
        preparedStatement.setString(1,newBZ);
        preparedStatement.setString(2,ID);
        preparedStatement.execute();
        myConnection.Close();
    }
}
