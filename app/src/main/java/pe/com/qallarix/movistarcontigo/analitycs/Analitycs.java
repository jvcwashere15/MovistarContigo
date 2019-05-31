package pe.com.qallarix.movistarcontigo.analitycs;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

import pe.com.qallarix.movistarcontigo.descuentos.pojos.Discount;
import pe.com.qallarix.movistarcontigo.salud.pojos.Plan;

public class Analitycs {


    public static void logEventoLogin(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.LOGIN,null);
    }


    public static void logEventoClickDashboard(Context context, String dashboardTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.DASHBOARD_TITLE,dashboardTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_DASHBOARD,null);
    }


    public static void logEventoClickItemDescuento(Context context, Discount discount){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.DISCOUNT_ID,discount.getId());
        params.putString(ParamsAnalitycs.DISCOUNT_BRAND, discount.getBrand());
        params.putString(ParamsAnalitycs.DISCOUNT_DESCRIPTION,discount.getDescription());
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_CELL_DISCOUNT_CLICKED,params);
    }


    public static void logEventoClickDashboardVerMasNoticia(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_NEWS_DASHBOARD,null);
    }


    public static void logEventoClickBottomNav(Context context, String bottomNavTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.BOTTOM_NAV_TITLE,bottomNavTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_BOTTOM_NAV,null);
    }


    public static void logEventoClickHerramientas(Context context, String toolTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.TOOL_TITLE,toolTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_TOOL,null);
    }


    public static void logEventoClickHerramientaLink(Context context,String toolLinkTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.TOOL_LINK_TITLE,toolLinkTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_TOOL_LINK,null);
    }



    public static void logEventoClickBenefits(Context context,String benefitTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.BENEFIT_TITLE,benefitTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_BENEFITS,params);
    }


    public static void logEventoClickPlanSalud(Context context, Plan plan){
        Bundle params = new Bundle();
        params.putInt(ParamsAnalitycs.HEALTH_PLAN_ID,(int)plan.getID());
        params.putString(ParamsAnalitycs.HEALTH_PLAN_TITLE, plan.getTitle());
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_HEALTH_PLAN,null);
    }

    public static void setUserPropertyClaseYCategoria(Context context, String clase, String category) {
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_AREA",clase);
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_PROFILE",category);
    }
}
