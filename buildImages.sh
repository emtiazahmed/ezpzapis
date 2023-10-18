#!/usr/bin/env bash

set -eo pipefail

modules=( tenants-service metastore-service api-gateway )

for module in "${modules[@]}"; do
    docker build -t "ezpzapis/${module}:latest" ${module} --no-cache
done
