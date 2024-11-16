/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

import java.util.ArrayList;
import java.util.List;

public class ListaMiBean {
    private List<MiBean> listaDatos = new ArrayList<>();

    public void agregarDatos(MiBean bean) {
        listaDatos.add(bean);
    }

    public List<MiBean> getListaDatos() {
        return listaDatos;
    }
}
