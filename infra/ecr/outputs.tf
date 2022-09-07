output "ecr_repository_url" {
    value = aws_ecr_repository.ecr_springboot.repository_url
}

output "ecr_name" {
    value = aws_ecr_repository.ecr_springboot.name
}
