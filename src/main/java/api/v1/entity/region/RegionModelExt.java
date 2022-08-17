package api.v1.entity.region;

public interface RegionModelExt extends RegionModel {
    public <T extends Enum<T> & RegionModel> T getParent();
}
