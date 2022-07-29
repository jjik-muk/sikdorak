# Infra Provisioning for BE

### Infra Architecture

- v0.0.1
  - 초기 백엔드 서버는 기능 개발이 완료되기 전까지 Heroku 단일 앱서버를 사용합니다.

### RUN

- 준비물
  - install Teraform
  - Heroku
    - heroku email
    - heroku private api key
    - heroku app name

```bash
# terraform setup
terraform init

# terraform 계획 확인
terraform plan

# terraform 프로비저닝 적용
terraform apply

# 프로비저닝 자원 제거
terraform destroy
```

### Reference

- https://registry.terraform.io/providers/heroku/heroku/latest/docs
- https://www.heroku.com/
