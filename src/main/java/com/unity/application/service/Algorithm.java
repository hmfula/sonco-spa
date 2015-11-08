package com.unity.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harry on 26.7.2015.
 */
public class Algorithm {
    List<Cell> scope = new ArrayList<Cell>();
    List<Cell>  validDcope = new ArrayList<Cell>();
    List <Cell> cells = new ArrayList<Cell>();

    void  preCheck(){
        for(Cell cell: cells){

            if(cell.getDn()!= null && cell.getLoadKpiValue() >= 70 && cell.getUlInterferenceKpiValue()<=8){
                validDcope.add(cell);
            }
        }

    }
    public  void  loadCM(List <Cell> cells){

    }
    public  void  loadPM(List <Cell> cells){

    }

    public  void  run(List <Cell> cells){
        for(Cell cell: cells){

            if(cell.getLoadKpiValue()>= 70 ){
                Float power =  cell.getPilotPower();
                cell.setPilotPower(power - 0.5f);
            }else if (cell.getPilotPower()== 33  ){
                continue;
            }else {
                Float power =  cell.getPilotPower();
                cell.setPilotPower(power + 0.5f);
            }
        }
    }
    private class KpiContainer{
        Map<String, List <Kpi> > dnKpiListMap = new HashMap<String, List <Kpi>>();

        private Map<String, List<Kpi>> getDnKpiListMap() {
            return dnKpiListMap;
        }

        private void setDnKpiListMap(Map<String, List<Kpi>> dnKpiListMap) {
            this.dnKpiListMap = dnKpiListMap;
        }

    }

    private class Kpi {
        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

        private Float getValue() {
            return value;
        }

        private void setValue(Float value) {
            this.value = value;
        }

        String name;
        Float value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Kpi kpi = (Kpi) o;

            if (!name.equals(kpi.name)) return false;
            if (!value.equals(kpi.value)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    private class Cell {

        private Float power;

        private Float getLoadKpiValue() {
            return nameKpiValueMap.get("load");
        }

        private void setLoadKpiValue(Float nameKpiValueMap) {
            this.nameKpiValueMap.put("load",nameKpiValueMap);
        }

        private Float getUlInterferenceKpiValue() {
            return nameKpiValueMap.get("ul-interference");
        }

        private void setUlInterferenceKpiValue(Float nameKpiValueMap) {
            this.nameKpiValueMap.put("ul-interference",nameKpiValueMap);
        }


        private Map<String, Float> getNameKpiValueMap() {
            return nameKpiValueMap;
        }

        private void setNameKpiValueMap(Map<String, Float> nameKpiValueMap) {
            this.nameKpiValueMap = nameKpiValueMap;
        }

        private String getDn() {
            return dn;
        }

        private void setPilotPower(Float power) {
            this.power = power;
        }

        private Float getPilotPower() {
            return power;
        }

        private void setDn(String dn) {
            this.dn = dn;
        }
        Map <String, Float>  nameKpiValueMap = new HashMap <String, Float>();

        String dn;
    }
}
