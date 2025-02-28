# File to keep track of necessary setup steps

helm repo add kubeflow https://alauda.github.io/kubeflow-chart

helm install my-kubeflow kubeflow/kubeflow --version 1.6.2