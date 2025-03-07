from cassandra.cluster import Cluster, Session

def get_cassandra_session() -> Session:
    cluster = Cluster([""])
    session = cluster.connect()
    try:
        yield session
    finally:
        session.shutdown()
        cluster.shutdown()