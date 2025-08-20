-- V1__Create_car_tables.sql
-- 차량 모델 테이블 생성
CREATE TABLE car_model (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    url TEXT,
    fuel_type VARCHAR(20),
    fuel_efficiency NUMERIC(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 차량 테이블 생성
CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    car_model_id BIGINT NOT NULL,
    plate_no VARCHAR(20) NOT NULL UNIQUE,
    image_url TEXT,
    fuel_type VARCHAR(20),
    efficiency_km_per_l NUMERIC(10,2),
    status VARCHAR(20),
    car_type VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

-- 인덱스 생성
CREATE INDEX idx_car_plate_no ON car(plate_no);
CREATE INDEX idx_car_car_model_id ON car(car_model_id);
CREATE INDEX idx_car_status ON car(status);

-- 차량 모델 테이블에 샘플 데이터 삽입 (선택사항)
INSERT INTO car_model (name, url, fuel_type, fuel_efficiency) VALUES
('현대 아반떼', 'https://example.com/avante.jpg', '휘발유', 15.5),
('현대 투싼', 'https://example.com/tucson.jpg', '휘발유', 12.8),
('기아 K5', 'https://example.com/k5.jpg', '휘발유', 14.2),
('기아 스포티지', 'https://example.com/sportage.jpg', '휘발유', 13.5),
('쌍용 티볼리', 'https://example.com/tivoli.jpg', '휘발유', 14.0);

-- 시퀀스 값 조정 (샘플 데이터 삽입 후)
SELECT setval('car_model_id_seq', (SELECT MAX(id) FROM car_model));
