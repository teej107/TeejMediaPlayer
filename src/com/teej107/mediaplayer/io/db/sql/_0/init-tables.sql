CREATE TABLE IF NOT EXISTS music
(
  uri TEXT PRIMARY KEY,
  title TEXT,
  artist TEXT,
  album TEXT,
  year INT,
  duration_sec INT
)