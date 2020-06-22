package api.service;

import api.SqlMapClient;
import api.model.APIParam;
import api.model.APIVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class APIService {

    public void getApiData(APIParam param) throws Exception {

        String API_URL = param.getApiUrl();
        String SERVICE_KEY = param.getServiceKey();

        String BASE_API_URL = API_URL + "?key" + "=" + SERVICE_KEY;

        String CUR_PAGE	= makeQueryString("curPage", param.getCurPage());
        String ITEM_PER_PAGE = makeQueryString("itemPerPage", param.getItemPerPage());
        String MOVIE_NM = makeQueryString("movieNm", param.getMovieNm());
        String DIRECTOR_NM = makeQueryString("directorNm", param.getDirectorNm());
        String OPEN_START_DT = makeQueryString("openStartDt", param.getOpenStartDt());
        String OPEN_END_DT = makeQueryString("openEndDt", param.getOpenEndDt());
        String PRDT_START_YEAR = makeQueryString("prdtStartYear", param.getPrdtStartYear());
        String PRDT_END_YEAR = makeQueryString("prdtEndYear", param.getPrdtEndYear());
        String REP_NATION_CD = makeQueryString("repNationCd", param.getRepNationCd());
        String MOVIE_TYPE_CD = makeQueryString("movieTypeCd", param.getMovieTypeCd());

        String urlSet = BASE_API_URL + CUR_PAGE + ITEM_PER_PAGE + MOVIE_NM + DIRECTOR_NM
                + OPEN_START_DT + OPEN_END_DT + PRDT_START_YEAR + PRDT_END_YEAR + REP_NATION_CD + MOVIE_TYPE_CD;

        URL url = new URL(urlSet);
        System.out.println("-------------REQUEST URL-------------");
        System.out.println(url);
        System.out.println("-------------REQUEST URL-------------");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("RESPONSE CODE : "  + conn.getResponseCode());

        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            // 정상 반환처리
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // StringBuilder VS StringBuffer
        // https://novemberde.github.io/2017/04/15/String_0.html
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();

        String res = sb.toString();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(res);
        JSONObject underObj = (JSONObject) jsonObject.get("movieListResult");
        JSONArray arr = (JSONArray) underObj.get("movieList");

        List<APIVO> apivoList = new ArrayList<APIVO>();

        System.out.println(" - - - - - - - - - - - - - - - - RESULT - - - - - - - - - - - - - - - - -");

        for (int i=0; i<arr.size(); i++) {
            JSONObject jObj = (JSONObject) arr.get(i);
            System.out.println(jObj);
            apivoList.add(makeVoModel(jObj));
        }

        System.out.println(" - - - - - - - - - - - - - - - - VO LIST RESULT - - - - - - - - - - - - - - - - -");
        System.out.println(apivoList.toString());

        SqlSession session = SqlMapClient.getSqlSession();
        for (int j=0; j<apivoList.size(); j++) {
            session.insert("api.test.test", apivoList.get(j));
        }
        session.commit();
        session.close();

    }

    public String makeQueryString(String fieldName, String str) throws Exception {
        return "&" + fieldName + "=" + URLEncoder.encode(str, "UTF-8");
    }

    public APIVO makeVoModel(JSONObject obj) {
        APIVO voModel = new APIVO();

        String repNationNm = (String) obj.get("repNationNm");
        String peopleNm = (String) ((JSONObject) ((JSONArray) obj.get("directors")).get(0)).get("peopleNm");
        String nationAlt = (String) obj.get("nationAlt");
        String repGenreNm = (String) obj.get("repGenreNm");
        String movieNm = (String) obj.get("movieNm");
        String movieCd = (String) obj.get("movieCd");
        String prdtStatNm = (String) obj.get("prdtStatNm");
        String prdtYear = (String) obj.get("prdtYear");
        String typeNm = (String) obj.get("typeNm");
        String openDt = (String) obj.get("openDt");
        String movieNmEn = (String) obj.get("movieNmEn");
        String genreAlt = (String) obj.get("genreAlt");

        voModel.setRepNationNm(repNationNm);
        voModel.setPeopleNm(peopleNm);
        voModel.setNationAlt(nationAlt);
        voModel.setRepGenreNm(repGenreNm);
        voModel.setMovieNm(movieNm);
        voModel.setMovieCd(movieCd);
        voModel.setPrdtStatNm(prdtStatNm);
        voModel.setPrdtYear(prdtYear);
        voModel.setTypeNm(typeNm);
        voModel.setOpenDt(openDt);
        voModel.setMovieNmEn(movieNmEn);
        voModel.setGenreAlt(genreAlt);

        return voModel;
    }

}
