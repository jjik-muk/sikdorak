# Infra 🏛

- Terraform을 활용하여 AWS 서비스를 프로비저닝 합니다.
- sonarcloud를 활용하여 Terraform 코드를 [정적분석](https://sonarcloud.io/project/overview?id=sikdorak-infra)을 합니다.


# 프로젝트 구조 🏗

## 소스코드 📂

```bash
.
├── README.md
├── FE_s3_cloudfront # Frontend 팀 정적 리소스 저장소(S3)와 CDN
├── s3_images # 이미지 전용 저장소(S3)
├── be # sonarcloud와 Backend 팀 테스트 서버(heroku) 
├── ecr # Backend 팀 Spring container image 저장소(ECR)
├── ecs # Backend 팀 컨테이너 이미지 실행 컴퓨팅(ECS)
├── rds # Backend 팀 MySQL 데이터베이스(RDS)
├── iam # 관리자 아이디 관리(IAM)
└── vpc # 공통으로 사용할 네트워크(VPC)
```

## 프로젝트 아키텍처 🏛

![프로젝트아키텍처](https://user-images.githubusercontent.com/57086195/189524880-6e3bfb26-1cb8-4e74-9461-f97361308095.png)


# 프로젝트 기술 스택 🪛

### Infra

<img src="https://img.shields.io/badge/Terraform-7B42BC?style=flat&logo=terraform&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=flat&logo=github actions&logoColor=white"/> <img src="https://img.shields.io/badge/SonarCloud-F3702A?style=flat&logo=sonarcloud&logoColor=white"/> 

 <img src="https://img.shields.io/badge/AWS S3-569A31?style=flat&logo=Amazon s3&logoColor=white"/> <img src="https://img.shields.io/badge/AWS CloudFront-FF9900?style=flat&logo=&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white"/>  <img src="https://img.shields.io/badge/AWS ECS-FF9900?style=flat&logo=Amazon ecs&logoColor=white"/> <img src="https://img.shields.io/badge/AWS ECR-FF9900?style=flat&logo=Aws&logoColor=white"/> <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=flat&logo=Amazon rds&logoColor=white"/> 

