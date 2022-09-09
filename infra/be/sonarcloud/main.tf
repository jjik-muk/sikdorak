terraform {
  required_providers {
    sonarcloud = {
      source = "rewe-digital/sonarcloud"
      version = "0.2.1"
    }
  }
}

provider "sonarcloud" {
  organization = var.sonarcloud_organization
  token        = var.sonarcloud_token
}