package pe.com.qallarix.movistarcontigo.embajador.total.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.adapters.BeneficioAdapter;
import pe.com.qallarix.movistarcontigo.embajador.FragmentParentEmbajador;
import pe.com.qallarix.movistarcontigo.pojos.Beneficio;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorTotalBeneficiosFragment extends FragmentParentEmbajador {


    private ViewPager vpBeneficios;
    private ViewPager vpTodos;
    private ViewPager vpConsideraciones;
    private ViewPager vpRestricciones;

    private List<Beneficio> beneficios;
    private List<Beneficio> todos;
    private List<Beneficio> consideraciones;
    private List<Beneficio> restricciones;

    private ImageView ivBeneficiosAtras,ivBeneficiosAdelante,
            ivTodosAtras,ivTodosAdelante,
            ivConsideracionesAtras,ivConsideracionesAdelante,
            ivRestriccionesAtras,ivRestriccionesAdelante;

    public EmbajadorTotalBeneficiosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_embajador_total_beneficios, container, false);
        vpBeneficios = rootView.findViewById(R.id.embajador_movistar_total_vpBeneficios);
        vpTodos = rootView.findViewById(R.id.embajador_movistar_total_vpTodosPueden);
        vpConsideraciones = rootView.findViewById(R.id.embajador_movistar_total_vpConsideraciones);
        vpRestricciones = rootView.findViewById(R.id.embajador_movistar_total_vpRestricciones);
        ivBeneficiosAtras = rootView.findViewById(R.id.total_beneficios_ivAtras);
        ivBeneficiosAdelante = rootView.findViewById(R.id.total_beneficios_ivAdelante);
        ivTodosAtras = rootView.findViewById(R.id.total_todos_ivAtras);
        ivTodosAdelante = rootView.findViewById(R.id.total_todos_ivAdelante);
        ivConsideracionesAtras = rootView.findViewById(R.id.total_consideraciones_ivAtras);
        ivConsideracionesAdelante = rootView.findViewById(R.id.total_consideraciones_ivAdelante);
        ivRestriccionesAtras = rootView.findViewById(R.id.total_restricciones_ivAtras);
        ivRestriccionesAdelante = rootView.findViewById(R.id.total_restricciones_ivAdelante);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargaDataBeneficios();
        cargaDataTodosPueden();
        cargaDataConsideraciones();
        cargaDataRestricciones();

        configurarFlechasViewPager(vpBeneficios,ivBeneficiosAtras,ivBeneficiosAdelante,beneficios.size()-1);
        configurarFlechasViewPager(vpTodos,ivTodosAtras,ivTodosAdelante,todos.size()-1);
        configurarFlechasViewPager(vpConsideraciones,ivConsideracionesAtras,ivConsideracionesAdelante,consideraciones.size()-1);
        configurarFlechasViewPager(vpRestricciones,ivRestriccionesAtras,ivRestriccionesAdelante,restricciones.size()-1);


        BeneficioAdapter beneficioAdapter = new BeneficioAdapter(getChildFragmentManager(),beneficios
                ,getResources().getColor(R.color.colorTextoCanalEmbajador));
        BeneficioAdapter todosAdapter = new BeneficioAdapter(getChildFragmentManager(),todos
                ,getResources().getColor(R.color.colorTextoCanalEmbajador));
        BeneficioAdapter consideracionesAdapter = new BeneficioAdapter(getChildFragmentManager(),consideraciones
                ,getResources().getColor(R.color.colorTextoCanalEmbajador));
        BeneficioAdapter restriccionesAdapter = new BeneficioAdapter(getChildFragmentManager(),restricciones
                ,Color.WHITE);

        vpBeneficios.setAdapter(beneficioAdapter);
        vpTodos.setAdapter(todosAdapter);
        vpConsideraciones.setAdapter(consideracionesAdapter);
        vpRestricciones.setAdapter(restriccionesAdapter);

    }

    public void cargaDataBeneficios(){
        beneficios = new ArrayList<>();
        beneficios.add(new Beneficio(
                  R.drawable.ic_beneficio_1,
                "Deco Smart",
                "¡Experimenta una manera distinta de ver televisión!"
                ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_2,
                "Movistar Play",
                "¡Disfruta el mejor contenido desde dónde estés!"
        ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_3,
                "Movistar Prix",
                "¡Disfruta de nuestro programa de beneficios exclusivos!"
        ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_4,
                "Minutos Ilimitados",
                "Minutos ilimitados a cualquier operador a EEUU y Canadá"
        ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_5,
                "Apps Ilimitadas",
                "¡Ponte en modo ilimitado con nuestros beneficios!"
        ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_6,
                "Gigas Internacionales",
                "Hasta 12 GB Internacionales para cada línea."
        ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_7,
                "Financiamiento Móvil",
                "Pagas tus cuotas desde tu celular de la manera más fácil."
        ));
        beneficios.add(new Beneficio(
                R.drawable.ic_beneficio_8,
                "Smart Wifi",
                "¡Prepárate para navegar sin parar!"
        ));
    }

    public void cargaDataTodosPueden(){
        todos = new ArrayList<>();
        todos.add(new Beneficio(
                R.drawable.ic_todos_pueden_1,
                "Tengo Movistar Hogar",
                "Si tienes cualquier servicio movistar en tu hogar, solicita Movistar Total"
        ));
        todos.add(new Beneficio(
                R.drawable.ic_todos_pueden_2,
                "Tengo Movistar Móvil",
                "Si tienes una o más líneas móviles, estás a un paso de ser Movistar Total"
        ));
        todos.add(new Beneficio(
                R.drawable.ic_todos_pueden_3,
                "Tengo Movistar Hogar + Móvil",
                "Si tienes cualquier servicio movistar en tu hogar y un móvil, ya puedes ser Movistar Total"
        ));
    }

    public void cargaDataConsideraciones(){
        consideraciones = new ArrayList<>();
        consideraciones.add(new Beneficio(
                R.drawable.ic_consideracion_1,
                "",
                "Alcance sólo al personal activo de Telefónica del Perú S.A.A."
        ));
        consideraciones.add(new Beneficio(
                R.drawable.ic_consideracion_2,
                "",
                "El colaborador sólo podrá contratar 01 Movistar Total."
        ));
        consideraciones.add(new Beneficio(
                R.drawable.ic_consideracion_3,
                "",
                "Los servicios móviles asociados a MT cambiarán sus fecha de facturación ajustándose a los ciclos de la fija."
        ));
        consideraciones.add(new Beneficio(
                R.drawable.ic_consideracion_4,
                "",
                "El descuento de MT no es acumulable con otros descuentos."
        ));
        consideraciones.add(new Beneficio(
                R.drawable.ic_consideracion_5,
                "",
                "Las líneas fijas y móviles deben de estar a nombre del colaborador."
        ));
        consideraciones.add(new Beneficio(
                R.drawable.ic_consideracion_6,
                "",
                "El descuento se aplicará en el segundo recibo emitido."
        ));

    }

    public void cargaDataRestricciones(){
        restricciones = new ArrayList<>();
        restricciones.add(new Beneficio(
                R.drawable.ic_restriccion_1,
                "",
                "No aplica a titulares identificados con Carnet de Extranjería."
        ));
        restricciones.add(new Beneficio(
                R.drawable.ic_restriccion_2,
                "",
                "No aplica a líneas en STC."
        ));
        restricciones.add(new Beneficio(
                R.drawable.ic_restriccion_3,
                "",
                "Clientes con Movistar Seguro pierden el beneficio."
        ));
        restricciones.add(new Beneficio(
                R.drawable.ic_restriccion_4,
                "",
                "No aplica a planes inteligentes ni Ex Belsouth."
        ));
        restricciones.add(new Beneficio(
                R.drawable.ic_restriccion_5,
                "",
                "El beneficio en Movistar Total no aplica a filiales."
        ));
    }
}
