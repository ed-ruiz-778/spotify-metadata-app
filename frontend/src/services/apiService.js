import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/codechallenge',
    auth: {
        username: 'admin',
        password: 'password'
    }
});

export const createTrackByIsrc = (isrc) => {
    return apiClient.post(`/createTrack?isrc=${isrc}`);
};

export const getTrackMetadata = (isrc) => {
    return apiClient.get(`/getTrackMetadata?isrc=${isrc}`);
};

export const getCoverImage = (isrc) => {
    return apiClient.get(`/getCover?isrc=${isrc}`, {
        responseType: 'blob'
    });
};
