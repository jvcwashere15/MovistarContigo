package pe.com.qallarix.movistarcontigo.analitycs;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

import pe.com.qallarix.movistarcontigo.discounts.pojos.Discount;
import pe.com.qallarix.movistarcontigo.health.pojos.Plan;

public class Analitycs {


    public static void logEventoLogin(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.LOGIN,null);
    }


    public static void logEventoClickDashboard(Context context, String dashboardTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.DASHBOARD_TITLE,dashboardTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_DASHBOARD,params);
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


    public static void logEventoClickBottomNav(Context context, String title){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.BOTTOM_NAV_TITLE,title);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_BOTTOM_NAV,params);
    }


    public static void logEventoClickHerramientas(Context context, String toolTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.TOOL_TITLE,toolTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_TOOL,params);
    }


    public static void logEventoClickHerramientaLink(Context context,String toolLinkTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.TOOL_LINK_TITLE,toolLinkTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_TOOL_LINK,params);
    }



    public static void logEventoClickBenefits(Context context,String benefitTitle){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.BENEFIT_TITLE,benefitTitle);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_BENEFITS,params);
    }


    public static void logEventoClickPlanSalud(Context context, Plan plan){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.HEALTH_PLAN_TITLE, plan.getTitle());
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_HEALTH_PLAN,params);
    }

    public static void logEventoClickBotonPrix(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_PRIX_CLICKED,null);
    }

    public static void logEventoClickCampanaNotificaciones(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_NOTIFICATION_BELL,null);
    }


    public static void logEventoRegistroVacaciones(Context context, String date){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.VACATION_REGISTER_DATE, date);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_VACATION_REGISTER,params);
    }


    public static void logEventoAprobacionVacaciones(Context context, String date, String type){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.VACATION_APPROVE_DATE, date);
        params.putString(ParamsAnalitycs.VACATION_APPROVE_TYPE, type);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_VACATION_APPROVE,params);
    }

    public static void logEventoRegistroFlexPlace(Context context, String date){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.FLEXPLACE_REGISTER_DATE, date);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_FLEXPLACE_REGISTER,params);
    }

    public static void logEventoAprobacionFlexPlace(Context context, String date, String type){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.FLEXPLACE_APPROVE_DATE, date);
        params.putString(ParamsAnalitycs.FLEXPLACE_APPROVE_TYPE, type);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_FLEXPLACE_APPROVE,params);
    }

    public static void logEventoCancelacionFlexPlace(Context context, String date){
        Bundle params = new Bundle();
        params.putString(ParamsAnalitycs.FLEXPLACE_CANCEL_DATE, date);
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_FLEXPLACE_CANCEL,params);
    }

    public static void logEventoClickBotonReporteFlexPlace(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_REPORTE_FLEXPLACE,null);
    }

    public static void logEventoClickBotonAcercaDeFlexPlace(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_ABOUT_FLEXPLACE,null);
    }

    public static void logEventoClickBotonAcercaDeVacaciones(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(EventsAnalitycs.EVENT_BUTTON_ABOUT_VACATION,null);
    }

    public static void setUserProperties(Context context, String clase, String category,
                                         String vicePresidency, String management, String cip, String direction ) {
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_AREA",clase);
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_PROFILE",category);
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_VP",vicePresidency);
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_MANAGEMENT",management);
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_CIP",cip);
        FirebaseAnalytics.getInstance(context).setUserProperty("FOR_DIRECTION",direction);

    }
}
