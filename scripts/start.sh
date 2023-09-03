#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/eyeteaboard/project
PROJECT_NAME=eyeteaboard

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -t $REPOSITORY/*.jar | tail -n 1) # -t는 최신 수정순으로 정렬, -r은 반대로 정렬

echo "> JAR name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME를 profile-$IDLE_PROFILE 로 실행합니다."

nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,/home/ec2-user/eyeteaboard/application-mail.properties,\
/home/ubuntu/eyeteaboard/application-oauth.properties,/home/ec2-user/eyeteaboard/application-real-db.properties,classpath:/application-$IDLE_PROFILE.properties \
-Dspring.profiles.active=$IDLE_PROFILE \
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
