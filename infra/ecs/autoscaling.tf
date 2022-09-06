# autoscaling group 생성
resource "aws_launch_configuration" "ecs_launch_config" {
    image_id             = "ami-01d87646ef267ccd7" # Amazon Linux 2 AMI (HVM) - Kernel 5.10, SSD Volume Type
    iam_instance_profile = aws_iam_instance_profile.ecsTaskExecutionRole_profile.name
    security_groups      = [aws_security_group.ecs_springboot.id]
    user_data            = "#!/bin/bash\necho ECS_CLUSTER=my-cluster >> /etc/ecs/ecs.config"
    instance_type        = "t3.micro"
}

resource "aws_autoscaling_group" "asg-springboot" {
    name                      = "asg-springboot"
    vpc_zone_identifier       = [data.terraform_remote_state.vpc.outputs.vpc_public_subnet]
    launch_configuration      = aws_launch_configuration.ecs_launch_config.name

    desired_capacity          = 1
    min_size                  = 1
    max_size                  = 1
    health_check_grace_period = 30
    health_check_type         = "EC2"
}
