package api;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;
import java.io.Reader;

public class SqlMapClient {
    private static SqlSession _session = null;

    static {
        try {
            String resource = "MyBatisConfig.xml";
            System.out.println(resource);
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            _session = sqlMapper.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return _session;
    }


}
