# youthcon23

## 함께해요 멀티모듈

### 대상

- 멀티 모듈을 처음 시작 하시는 분을 대상으로 튜토리얼 느낌으로 해당 세션을 준비했습니다.

### 환경

- Spring 2.7.14
- JAVA 11
- Gradle 8.3

----

## 가이드
궁금한 점 or 제보는 audrn6689@gmail.com 으로 연락 주시면 답변해드리겠습니다! 

1. 예제 프로젝트는 실습을 복잡하게 하지 않기 위해
2. 아키텍처는 많이 익숙한 계층형으로 구성을 했습니다.
3. branch 별로 체크포인트를 만들었습니다.
- main : 시작 지점
- step-1 : api 모듈 만들기
- step-2 : core 모듈 만들기
- step-3 : admin 모듈 만들기

### Step-1 : API 모듈 만들어 보기
1. setting.gradle 에서 모듈 세팅이 가능 합니다.
2. include('api') 를 입력하시고 빌드 새로 고침을 해주세요.
3. 프로젝트 최상단(youthcon23)모듈 우 클릭 후 디렉토리 만들기 이름은 api 로 설정하시면 됩니다.
4. api 모듈에 기존 소스파일 전부 옮겨 주시고 hello.module 패키지 이름을 hello.api 로 적절하게 바꿔 봅시다.
5. <details><summary> 스프링 부트를 실행 해 봅시다.   </summary>

    빌드가 되지 않습니다. 이유는 gradle.build 를 통해 프로젝트를 빌드하거나 실행하기 때문입니다.   
    새로운 모듈을 만들고 모듈안에 있는 애플리케이션을 어떻게 실행할지 정보가 없기 때문이죠.

</details> 
 
6. 밖에 있는 build.gradle 을 복사해서 api 모듈에 옮겨보도록 하겠습니다.
7. <details><summary> 스프링 부트를 실행 해 봅시다.   </summary>

    현재 밖에 있는 build.gradle 이하 root gradle 과 api 모듈에 있는 build.gradle 는 복사를 했기 때문에 중복입니다.   
    gradle.build 를 통해 프로젝트를 빌드, 실행, 설정 할 수 있기 때문에 적절하게 build.gradle 을 설정하도록 하겠습니다.

</details>

8. <details><summary> root gradle 로 가서 allprojects, subprojects 설정을 해보겠습니다.   </summary>

   루트 모듈을 포함한 모든 모듈을 관리하는 allprojects, 루트 모듈을 제외한 모든 모듈을 관리하는 subproject
    저는 모든 모듈의 그룹과 버전, java 버전, repositoreies 를 공통적으로 사용할 것이기 때문에 allprojects로 옮겼습니다.   
   루트 모듈에서 가져온 플러그인 부분도 하위 모듈에 적용될 수 있도록 적절하게 설정하면 됩니다.
    ```groovy
    plugins {
        id 'java'
        id 'org.springframework.boot' version '2.7.14'
        id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    }
    
    allprojects {
        group = 'hello'
        version = '0.0.1-SNAPSHOT'

        java {
            sourceCompatibility = '11'
        }

        repositories {
            mavenCentral()
        }
    }
        
        subprojects {
        apply { plugin('java') }
        apply { plugin('org.springframework.boot') }
        apply { plugin('io.spring.dependency-management') }
        
        tasks.named('test') {
            useJUnitPlatform()
        }
    }


    ```


</details>

### Step-2 : CORE 모듈 만들어 보기
1. 새로운 모듈을 만들기 위해 settings.gradle 에서 include('core') 입력하기.
2. <details><summary> 생성된 core 모듈에 src/main/java/hello.core 경로 만들기   </summary>

    core 모듈 우클릭 후 new -> Directory 하면 src/main/java 를 빠르게 만들 수 있습니다.      
    루트 gradle 에서 현재 설정된 group 은 hello 이므로 hello 패키지를 만들고, 하위에 core 모듈아래 작업하니 core 라는 하위패키지를 만듭니다.

</details>

3. core 모듈에도 build.gradle 이 필요하기 때문에 복사해서 가져와 줍니다.
4. <details><summary> core 모듈에 필요없는 의존성들을 제거 해 봅시다.   </summary>

    core 모듈 우클릭 후 new -> Directory 하면 src/main/java 를 빠르게 만들 수 있습니다.      
    루트 gradle 에서 현재 설정된 group 은 hello 이므로 hello 패키지를 만들고, 하위에 core 모듈아래 작업하니 core 라는 하위패키지를 만듭니다.   
    web, thymeleaf, h2 database 는 제가 정의한 core 모듈에는 맞지 않는 의존성 이므로 제거합니다.

</details>

5. api 모듈에도 사용하지 않는 의존성 (jpa) 를 제거합니다.
6. 빌드 새로고침을 하고, 이제는 존재하지 않는 hello.api.application, domain 부분을 core 로 변경합니다.
7. api 의 모듈이 core 모듈을 의존할 수 있게 설정해 줍니다. 
8. core 모듈이 제대로 import 된 것을 확인하고 스프링 부트를 실행을 해봅니다.
9. bean 문제를 해결해 봅니다.
10. 현재는 api gradle 만 build 를 하고 있었는데 각각 gradle(root, core) 을 build 해봅시다.  
