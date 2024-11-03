package ch.hatbe2113.abcchat.networkPackage;

import java.util.ArrayList;
import java.util.List;

public interface AbstractNetworkPackage {
    List<String> data = new ArrayList<>();
    void handle();
}
