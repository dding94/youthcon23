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
궁금한 점 or 잘못 된 부분 제보는 audrn6689@gmail.com 으로 연락 주시면 감사하겠습니다! 

### 모듈 ?
- 일반적으로 큰 체계의 구성요소이고, 다른 구성요소와 독립적으로 운영된다. 
- 특정 기능을 중심으로 독립적으로 운영될 수 있는 의미를 가지는 구성요소 단위

- 멀티 모듈을 사용하게 된다면, 이런 복잡한 의존성을 가진 프로젝트를 모듈화를 통해 책임과 기능을 분리 할 수 있고, 각 모듈들이 최소한의 의존성을 갖도록 구성할 수 있습니다. 이렇게 설정된 모듈의 의존성을 개발자는 강제적으로 지키며 개발이 가능해 집니다.
- 잘 이해가 안된다면 우리가 읽는 책들을 떠올려 보면 될 것 같습니다. 책에는 여러 챕터로 나누어져 해당 챕터에 맞는 내용들이 응집되어 있고, 해당 챕터를 위해 모든 페이지를 읽을 필요는 없습니다. 하지만 전체 스토리를 이해하려면 모든 챕터를 읽어야 합니다. 이처럼 멀티모듈도 여러 챕터로 구성되어 하나의 큰 스토리를 만드는게 목적이라고 생각합니다.

### 

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
9. <details><summary> 실행 후 오류를 보니 bean 관련 에러가 발생합니다. 문제를 해결해 봅니다.   </summary>

    @SpringBootApplication 의 동작방식을 이해하셔야 합니다.   
    해당 어노테이션이 위치하는 패키지와 하위 경로의 빈들을 스캔하기 때문에 hello.api 패키지에 존재하는 해당 어노테이션은 hello.core 의 패키지에 등록된 빈은 스캔할 수 없습니다.   
    1. hello.core 패키지도 스캔할 수 있게 scanBasePackage 를 설정.
    2. hello.api 패키지 에 존재하는 어노테이션을 hello 로 이동.   

    저는 두번째 방법을 선택했습니다. 추후 모듈이 늘어나도 직접 스캔 대상을 지정할 필요 없고, OCP의 원칙을 지킨다 생각하기 때문입니다.
    
</details>

10. 현재는 api gradle 만 build 를 하고 있었는데, 각각 gradle(root, core) 을 직접 build 해봅시다.  
11. <details><summary> gradle build error 가 발생하는 문제를 해결해 봅시다.   </summary>

    ./gradlew build 를 입력하시거나 우측에 직접 gradle ui 창을 통해 빌드를 하면 에러가 발생했을겁니다.   
    그 전에 잘 되는 이유는 api 모듈을 빌드한 것였고, bootJar Task 작업이 실패했다고 예외를 확인해주시면 됩니다.   
    BootJar는 Spring Boot 기능이 포함된 실행 가능한 JAR 파일로 패키징 합니다.      
    해당 Task 는 default 로 true 이기때문에 @SpringBootApplication 가 없는 Core 모듈은 bootJar 작업에 사용할 기본 클래스를 결정하지 못했음으로 예외가 발생하게 된 것 입니다.

</details>

12. Task 들의 설정을 진행하기 위해 루트 gradle 로 이동합니다.
13. <details><summary> 토글을 열어 설정을 합니다.   </summary>

    ```groovy
    
    plugins {
        id 'java'
        id 'org.springframework.boot' version '2.7.14' apply(false)
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
    
        tasks.named('bootJar') {
            enabled = false
        }
    
        tasks.named('jar') {
            enabled = true
        }
    
        tasks.named('test') {
            useJUnitPlatform()
        }
    }
    ```

    1. Jar 는 Spring Boot 기능 없이 클래스 및 리소스를 표준 JAR 파일로 패키징합니다. 애플리케이션을 실행하기 위한 내장된 톰캣을 포함하지 않습니다.
    2. BootJar 는Spring Boot 기능이 포함된 실행 가능한 JAR 파일로 패키징 합니다. 해당 Task 는 default 로 true 이기때문에 @SpringBootApplication 가 없는 Core 모듈은 bootJar 작업에 사용할 기본 클래스를 결정하지 못했음으로 예외가 발생하게 된 것 입니다.
    3. 상단 plugins 의 apply(false) 가 있는데 위와 같이 사용한 이유는 루트에서는 스프링 부트가 필요하지 않고, 플러그인의 정보들이 루트 프로젝트에서는 로드되지만 실제 플러그인 기능은 루트 프로젝트에 적용하지 않고 나중에 서브 프로젝트에서 명시적으로 적용할 수 있기 때문입니다.

</details>

14. step-2 가 완료되었습니다. 혹시 문제 해결이 잘 안되는 경우 step-2 branch 로 이동하여 확인하면 됩니다.


### Step-3 :  admin 모듈 만들어 보기
1. 모듈 두개만 만드는데 정말 많은 설명을 드린 것 같습니다. 따라오지 못하신분들은 branch 에서 step-2 를 누르시고 따라 하시면 됩니다.
2. 현재 api 모듈에 사용자를 위한, 관리자를 위한 기능을 분리 해보도록 하겠습니다.
3. 이제 모듈을 만드시는건 여러분도 쉽게 만들 것 같습니다. setting.gradle 에 가서 include(’admin’) 을 하시고 디렉토리를 만들어 줍니다. 새로고침은 항상 필수입니다!
4. java 디렉토리를 만들어주시고 패키지는 저희가 설정한 group 의 이름과 admin 모듈에서 사용하는 패키지 이름 admin 을 만들고 presentation 이 필요한데 이부분은 복사해오겠습니다.
5. 그리고 admin 모듈도 ppt 에서 설명했듯이 부트를 실행시키는 역할도 있기 때문에 @SpringBootApplication 가 있는 main 클래스가 필요합니다. 따라서 이 부분은 복사해와서 이름만 변경하도록 하겠습니다.
6. 해당 위치도 다시 강조하지만 @SpringBootApplication 의 스캔방식을 생각해서 hello 패키지에 위치하도록 복사해오겠습니다. 그리고 이름은 adminModuleApplication 으로 변경하도록 하겠습니다.
7. 그 다음으로 이제 의존성과, resources 파일이 필요하기 때문에 해당 부분도 복사해오도록 하겠습니다. 리소스는 메인에 복사하시고 gradle 은 모듈에 위치시키면 됩니다. 복사해왔으니까 꼭 적절하게 gradle 이 설정되어 있는지 확인하도록 하겠습니다.
8. 해당 admin 모듈에서 core 모듈을 의존하고 있고, 스프링을 실행하기 때문에 bootJar 도 정상이고, 나머지 부분도 정상입니다. 
9. 이제 api 모듈에는 이제 뷰 렌더링을 하는 기능이 필요없기 때문에 thymeleaf 를 지워주고, resources 에 templates 도 지워주도록 하겠습니다.
10. admin 모듈 스프링 부트 실행 , api 모듈 스프링 부트 실행 잘 되는 것을 확인할 수 있습니다.

### 장단점
장점
1. 모듈간 응집도를 높혀 프로젝트 파악이 쉽다.
    - 단일 모듈 구조와 달리 이제 각각의 모듈이름 또는 gradle 의 의존성이 어떻게 설정되어 있는지 봐도 어떤 역할을 하는 모듈인지 쉽게 파악 가능하다.
2.  개발 배포 독립성
    - admin, api 모듈은 서로간의 의존성이 없고, 서로 다른 배포주기를 가질 수 있습니다.
      
단점
1. 모듈간의 종속성 관리를 잘 못 한다면, 복잡한 프로젝트가 될 수 있습니다.
   - 어떤 기준으로 나눠야 하는지, 어떤 모듈이 어떤 모듈을 의존해야하는지
3. 초기 설정의 어려움
   - 익숙하지 않다면 개발 시간이 더 오래 걸릴 수 있습니다.
