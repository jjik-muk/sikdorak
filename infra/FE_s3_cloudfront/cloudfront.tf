# https://github.com/terraform-aws-modules/terraform-aws-cloudfront

module "sikdorak-cdn" {
  source = "terraform-aws-modules/cloudfront/aws"
  version = "2.9.3"

# 현재 domain 없음, 추가한다면 레코드 작업 A 수동으로 추가해야함
#  aliases = ["sikdorak.com"]

  comment             = "식도락 웹 서비스 by React"
  enabled             = true
  is_ipv6_enabled     = true
  price_class         = "PriceClass_All"
  retain_on_delete    = true
  wait_for_deployment = true
  default_root_object = "index.html"

#  restrictions {
#    geo_restriction {
#        locations        = ["US", "CA", "GB", "DE"]
#    }
#  }

  create_origin_access_identity = true
  origin_access_identities = {
    sikdorak-fe = "Access for sikdorak web"
  }

  origin = {
    sikdorak-fe = {
      domain_name = "sikdorak-fe.s3.ap-northeast-2.amazonaws.com"
      s3_origin_config = {
        origin_access_identity = "sikdorak-fe" 
        # cloudfront 오리진 설정 버킷 정책 업데이트 설정으로 수동 추가해야함
      }
    }
  }

  default_cache_behavior = {
    target_origin_id           = "sikdorak-fe"
    viewer_protocol_policy     = "redirect-to-https"

    allowed_methods = ["GET", "HEAD", "OPTIONS"]
    cached_methods  = ["GET", "HEAD"]
    compress        = true
    query_string    = true
  }

  viewer_certificate = {
    cloudfront_default_certificate = true
  }
}