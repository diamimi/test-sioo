package com;

import org.junit.Test;

/**
 * @Author: HeQi
 * @Date:Create in 16:24 2018/10/24
 */
public class RedisTest {

    /**
     * 导入号码到大黑名单
     */
    @Test
    public void llw(){

    }

   /* @Test
    private void saveBlackMobile(){
        try{
            RedisUtil rUtil = RedisUtil.getInstance();
            String data = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/sioowork/mongodbToTxt/hmd1022.txt"),"utf-8"));
            List<Long> list = new ArrayList<Long>();
            while((data = br.readLine()) != null){
                list.add(Long.parseLong(data));
            }
            br.close();
            int i = 0;
            StringBuilder sb = new StringBuilder();
            Map<Long, Integer> map = new HashMap<Long, Integer>();
            Connection conn = getConnectionToMySql21();
            PreparedStatement pstmt = null;
            int n = 0;
            for(Long l : list){
                i++;
                n++;
                sb.append(l).append(",");
                if(i >= 500){
                    pstmt = conn.prepareStatement("SELECT mobile FROM smshy.sms_black_mobile WHERE group_id=35 AND mobile in("+sb.substring(0, sb.length()-1)+");");
                    ResultSet rs = pstmt.executeQuery();
                    while(rs.next()){
                        map.put(rs.getLong("mobile"), 0);
                    }
                    rs.close();
                    i = 0;
                    sb.setLength(0);
                    pstmt.close();
                    System.out.println("---->select:"+n);
                }
            }
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO smshy.sms_black_mobile(mobile,group_id,addtime,mobileType,`level`)VALUES(?,?,?,?,?)");
            int j = 0;
            n =0;
            for(Long l : list){
                if(map.containsKey(l)){
                    continue;
                }
                j ++;
                n++;
                rUtil.setObject("35_"+l, "");
                try {
                    pstmt.setLong(1, l);
                    pstmt.setInt(2, 35);
                    pstmt.setLong(3, getYMDHMS(0));
                    pstmt.setInt(4, 3);
                    pstmt.setInt(5, 10);
                    pstmt.addBatch();
                    if(j>= 500){
                        j=0;
                        pstmt.executeBatch();
                        conn.commit();
                        pstmt.clearBatch();
                        System.out.println("---->insert:"+n);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(j > 0){
                pstmt.executeBatch();
                conn.commit();
                pstmt.clearBatch();
            }
            System.out.println("==========complete============");
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
}
