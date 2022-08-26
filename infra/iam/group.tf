resource "aws_iam_group" "be_group" {
    name = "be_Group"
    path = "/users/"
}

resource "aws_iam_group_policy_attachment" "be_group_policy_attach" {
    #name = aws_iam_group.be_group.name

    group = aws_iam_group.be_group.name

    policy_arn  = "arn:aws:iam::aws:policy/ReadOnlyAccess"
}


resource "aws_iam_user_group_membership" "be_group_membership" {
    count = length(var.users)
    
    user = element(var.users, count.index)

    groups = [
        aws_iam_group.be_group.name
    ]
}
