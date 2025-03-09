package com.pruebaconcepto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterForReflection
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StratusDTO {

    public String canalA;
    public String canalB;
    public String canalC;
    public String canalD;
    public String canalE;
    public String convenio;
    private String tipo;

    // Constructor
    public StratusDTO() {
    }

    public String getCanalA() {
        return canalA;
    }

    public String getCanalB() {
        return canalB;
    }

    public String getCanalC() {
        return canalC;
    }

    public String getCanalD() {
        return canalD;
    }

    public String getCanalE() {
        return canalE;
    }

    public String getConvenio() {
        return convenio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCanalA(String canalA) {
        this.canalA = canalA;
    }

    public void setCanalB(String canalB) {
        this.canalB = canalB;
    }

    public void setCanalC(String canalC) {
        this.canalC = canalC;
    }

    public void setCanalD(String canalD) {
        this.canalD = canalD;
    }

    public void setCanalE(String canalE) {
        this.canalE = canalE;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
