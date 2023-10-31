package com.zinoviev.questbot.OLD.bot.quest_management;

import com.zinoviev.sandbox.bot.entity.models.quest.BotGeoPoint;
import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuestMember;
import org.telegram.telegrambots.meta.api.objects.Location;

public class GeoPositionHandler {

    private final BotGeoPoint geoPoint;
    private final RunningBotQuestMember member;
    private final Location location;

    public GeoPositionHandler(BotGeoPoint geoPoint, RunningBotQuestMember member, Location location) {
        this.geoPoint = geoPoint;
        this.member = member;
        this.location = location;
    }

    public MemberGeoPointStatus check(){
        return checkLocation();
    }

    private MemberGeoPointStatus checkLocation() {
        MemberGeoPointStatus memberStatus = new MemberGeoPointStatus();

        double playerLatitude = location.getLatitude();
        double playerLongitude = location.getLongitude();

        double geoPointLatitude = geoPoint.getLatitude();
        double geoPointLongitude = geoPoint.getLongitude();

        //AtomicReference<Double> distance = new AtomicReference<>(99999999999999.999999999999999);
        double dist = getDistance(geoPointLatitude, geoPointLongitude, playerLatitude, playerLongitude);
        String strDist = "\nРасстояние до цели, примерно: "+String.valueOf(dist).substring(0,5)+" метров";
        System.out.println(strDist);
        memberStatus.setDistanceMessage(strDist);


        if (dist <= geoPoint.getGeoPointOuterRadius() && !member.isOutRadiusReached()){
            System.out.println("ВНЕШНИЙ РАДИУС");
            memberStatus.setRadiusMessages(geoPoint.getOuterRadiusReachedMessages());
            System.out.println(geoPoint.getOuterRadiusReachedMessages());
            member.setOutRadiusReached(true);
        }

        if (dist <= geoPoint.getGeoPointMeddleRadius() && !member.isMidRadiusReached()){
            System.out.println("ВНУТРЕННИЙ РАДИУС");
            memberStatus.setRadiusMessages(geoPoint.getMiddleRadiusReachedMessages());
            System.out.println(geoPoint.getMiddleRadiusReachedMessages());
            member.setMidRadiusReached(true);
        }

        if (dist <= geoPoint.getGeoPointRadius() && !member.isPointReached()){
            System.out.println("ТОЧКА ДОСТИГНУТА");
            memberStatus.setRadiusMessages(geoPoint.getOnPointMessages());
            System.out.println(geoPoint.getOnPointMessages());
            member.setPointReached(true);
        }


      return memberStatus;
    }

    private static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180/3.14169);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }
}
