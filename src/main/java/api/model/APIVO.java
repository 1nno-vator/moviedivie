package api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIVO {

    String movieCd;
    String movieNm;
    String movieNmEn;
    String prdtYear;
    String openDt;
    String typeNm;
    String prdtStatNm;
    String nationAlt;
    String genreAlt;
    String repNationNm;
    String repGenreNm;
    String directors;
    String peopleNm;
    String companys;
    String companyCd;
    String companyNm;

}
