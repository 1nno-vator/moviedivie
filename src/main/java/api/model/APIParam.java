package api.model;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Data
public class APIParam {

    String apiUrl;
    String serviceKey;
    String curPage;
    String itemPerPage;
    String movieNm; // UTF-8
    String directorNm; // UTF-8
    String openStartDt;
    String openEndDt;
    String prdtStartYear;
    String prdtEndYear;
    String repNationCd;
    String movieTypeCd;

    public APIParam(Properties props) throws UnsupportedEncodingException {
        this.apiUrl = props.getProperty("API_URL").trim();
        this.serviceKey = props.getProperty("SERVICE_KEY").trim();

        this.curPage = props.getProperty("curPage") != null ? props.getProperty("curPage").trim() : "1";
        this.itemPerPage = props.getProperty("itemPerPage") != null ? props.getProperty("itemPerPage").trim() : "10";
        this.movieNm = props.getProperty("movieNm") != null ? new String(props.getProperty("movieNm").getBytes("ISO-8859-1"), "UTF-8").trim() : "";
        this.directorNm = props.getProperty("directorNm") != null ? new String(props.getProperty("directorNm").getBytes("ISO-8859-1"), "UTF-8").trim() : "";
        this.openStartDt = props.getProperty("openStartDt") != null ? props.getProperty("openStartDt").trim() : "";
        this.openEndDt = props.getProperty("openEndDt") != null ? props.getProperty("openEndDt").trim() : "";
        this.prdtStartYear = props.getProperty("prdtStartYear") != null ? props.getProperty("prdtStartYear").trim() : "";
        this.prdtEndYear = props.getProperty("prdtEndYear") != null ? props.getProperty("prdtEndYear").trim() : "";
        this.repNationCd = props.getProperty("repNationCd") != null ? new String(props.getProperty("repNationCd").getBytes("ISO-8859-1"), "UTF-8").trim() : "전체";
        this.movieTypeCd = props.getProperty("movieTypeCd") != null ? new String(props.getProperty("movieTypeCd").getBytes("ISO-8859-1"), "UTF-8").trim() : "전체";
    }

}
