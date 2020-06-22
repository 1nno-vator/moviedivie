package api;

import api.model.APIParam;
import api.service.APIService;
import org.apache.ibatis.session.SqlSession;

public class APIController {

    public static void APICall(APIParam param) throws Exception {

        System.out.println("-- CONTROLLER START --");

        System.out.println("PARAM : ");
        System.out.println(param);

        APIService apiService = new APIService();
        apiService.getApiData(param);

        System.out.println("-- END --");

    }

}
