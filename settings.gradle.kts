rootProject.name = "order-system"

include(
    ":common",
    ":common:common-domain",
    ":common:common-application"
)
include(
    ":customer-service",
)

include(
    ":payment-service",
    ":payment-service:payment-dataaccess",
    ":payment-service:payment-domain",
    ":payment-service:payment-messaging",
    ":payment-service:payment-container",
    ":payment-service:payment-domain:payment-application-service",
    ":payment-service:payment-domain:payment-domain-core",


)

include(":order-service")
include(":order-service:order-dataaccess")
include(":order-service:order-domain")
include(":order-service:order-application")
include(":order-service:order-domain:order-application-service")
include(":order-service:order-domain:order-domain-core")
include(":order-service:order-container")
include(":order-service:order-application")
include(":order-service:order-messaging")

include(":infrastructure")
include(":infrastructure:kafka")
include(":infrastructure:kafka:kafka-config-data")
include(":infrastructure:kafka:kafka-consumer")
include(":infrastructure:kafka:kafka-model")
include(":infrastructure:kafka:kafka-producer")