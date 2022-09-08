# rds에서 사용할 보안그룹
resource "aws_security_group" "security_group_db" {
  name = "rds-mysql"

  vpc_id = data.terraform_remote_state.vpc.outputs.vpc_id

  ingress {
    protocol        = "tcp"
    from_port       = 3306
    to_port         = 3306
    cidr_blocks     = ["0.0.0.0/0"]
    security_groups = [data.terraform_remote_state.vpc.outputs.vpc_security_group_all]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "be"
  }
}
