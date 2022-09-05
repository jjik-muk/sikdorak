resource "aws_ecr_repository" "ecr_springboot" {
  name                 = "ecr-springboot"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = false
  }
}
