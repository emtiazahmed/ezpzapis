#!/usr/bin/env bash

set -eo pipefail

modules=( tenants-service metastore-service website )

for module in "${modules[@]}"; do
    docker build -t "ezpzapis/${module}:latest" ${module} --no-cache
done