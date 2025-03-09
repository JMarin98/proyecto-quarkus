package com.pruebaconcepto;

import jakarta.ws.rs.core.Response;

public interface ProcessResourceService {
    Response ProcessData(StratusDTO request);
}