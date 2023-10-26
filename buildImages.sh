#!/usr/bin/env bash

set -eo pipefail

modules=( api-gateway tenants-service metastore-service website )

for module in "${modules[@]}"; do
    docker image rm -f "ezpzapis/${module}:latest"
    docker build -t "ezpzapis/${module}:latest" ${module} --no-cache
done
