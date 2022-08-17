package api.v1.entity.region;

public enum RegionEnum implements RegionModel{
    경기도(1,"경기도"),
    서울특별시(2,"서울특별시")
    ;

    private final int key;
    private final String value;

    RegionEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
