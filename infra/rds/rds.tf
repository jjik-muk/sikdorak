# TODO
# [x] subnet group
# [x] db instance
# [x] parameter group
# [x] input : db id/pw  variable
# [x] ouput varibale : hostname, port
# [] +a) read replica 
# ref : https://learn.hashicorp.com/tutorials/terraform/aws-rds?in=terraform/aws

# subnet group : private subnet으로 구성된 RDS에 접근할 수 있는 서브넷 목록 입니다.
resource "aws_db_subnet_group" "db_subnet_group_sikdorak" {

  name = "db_subnet_sikdorak"

  subnet_ids = ["${data.terraform_remote_state.vpc.outputs.vpc_public_subnet}",
    "${data.terraform_remote_state.vpc.outputs.vpc_private_subnet}",
    "${data.terraform_remote_state.vpc.outputs.vpc_public_subnet2}",
    "${data.terraform_remote_state.vpc.outputs.vpc_private_subnet2}"
  ]

  tags = {
    Name = "be"
  }

}

resource "aws_db_instance" "db_sidkorak" {

  identifier        = "sikdorak-mysql"
  instance_class    = "db.t4g.micro" # free tier
  allocated_storage = 20
  engine            = "mysql"
  engine_version    = "8.0.28"
  db_name              = "sikdorak_db"
  username          = var.db_username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.db_subnet_group_sikdorak.name
  vpc_security_group_ids = [aws_security_group.security_group_db.id]
  parameter_group_name   = aws_db_parameter_group.korea_v1.name
  publicly_accessible    = false
  skip_final_snapshot    = false

}

resource "aws_db_parameter_group" "korea_v1" {
  name   = "rds-mydql"
  family = "mysql8.0"

  parameter {
    name  = "collation_server"
    value = "utf8mb4_general_ci"
  }

  parameter {
    name  = "collation_server"
    value = "utf8mb4_general_ci"
  }

  parameter {
    name  = "character_set_client"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_server"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_connection"
    value = "utf8mb4"
  }
  parameter {
    name  = "character_set_database"
    value = "utf8mb4"
  }
  parameter {
    name  = "character_set_filesystem"
    value = "utf8mb4"
  }
  parameter {
    name  = "character_set_results"
    value = "utf8mb4"
  }

  parameter {
    name  = "time_zone"
    value = "Asia/Seoul"
  }
}
