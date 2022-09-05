resource "aws_ecr_repository" "ecr_springboot" {
  name                 = "ecr-springboot"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = false
  }
}

resource "aws_ecr_lifecycle_policy" "ecr_policy_springboot" {
  repository = aws_ecr_repository.ecr_springboot.name

  policy = file("./json-policies/lifecyclepolicy.json")
}
