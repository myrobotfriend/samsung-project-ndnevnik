package com.doctorixx.eljur_analytics.instances;

import com.doctorixx.network.RequestClient;

public class ProdAnaliticsClient extends DebugAnaliticsClient {
    String SERVER_URL = "https://ndnevnik.pythonanywhere.com";

    public ProdAnaliticsClient(RequestClient requestClient) {
        super(requestClient);
    }

    @Override
    public String getHost() {
        return SERVER_URL;
    }
}
