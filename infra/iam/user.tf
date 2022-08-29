resource "aws_iam_user" "BE-users" {

    count = length(var.users)

    name = element(var.users, count.index)

    path = "/dev/"

    tags = {
    
        Name = "BE-developers"    

    }
}
