package jedistest;
import redis.clients.jedis.Jedis;
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.229.132",6379);
        String username = jedis.get("username");
        System.out.println(username);
    }
}
