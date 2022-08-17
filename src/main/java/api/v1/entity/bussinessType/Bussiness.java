package api.v1.entity.bussinessType;

import lombok.Getter;

@Getter
public enum Bussiness {
    농업(1, "농업, 임업 및 어업"),
    광업(2, "광업"),
    제조업(3,"제조업"),
    전기(4,"전기,가스,증기 및 수도업"),
    하수(5, "하수, 폐기물처리"),
    건설업(6, "건설업"),
    도매(7,"도매 및 소매업"),
    운수업(8, "운수업"),
    숙박(9, "숙박 및 음식점업(호텔)"),
    방송통신(10, "영상,출판, 방송통신 및 정보서비스업"),
    금융(11, "금융 및 보험업"),
    임대업(12,"부동산 및 임대업");

    private final int key;
    private final String value;


    Bussiness(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
