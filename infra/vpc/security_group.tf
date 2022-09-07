# ecs에서 사용할 보안그룹
resource "aws_security_group" "security_group_all" {
  name = "security_group_all"

  vpc_id = aws_vpc.vpc_be.id

  ingress {
    protocol        = "tcp"
    from_port       = 0
    to_port         = 65535
    cidr_blocks     = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "tcp"
    from_port   = 0
    to_port     = 65535
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "be"
  }
}
