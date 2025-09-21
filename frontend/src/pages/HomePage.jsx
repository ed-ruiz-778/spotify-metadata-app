import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useTrack } from '../hooks/useTrack'; // Updated import path
import { createTrackByIsrc } from '../services/apiService';
import SearchForm from '../components/SearchForm';
import { Alert, Box } from '@mui/material';

const HomePage = () => {
    const navigate = useNavigate();
    const { isLoading, setIsLoading, error, setError, setTrackData, setCoverUrl } = useTrack();

    const handleSearch = async (isrc) => {
        setIsLoading(true);
        setError(null);
        setTrackData(null);
        setCoverUrl(null);

        try {
            const response = await createTrackByIsrc(isrc);
            
            setTrackData(response.data);

            navigate(`/track/${isrc}`);

        } catch (err) {
            const errorMessage = err.response?.data?.message || 'An unexpected error occurred.';
            setError(errorMessage);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Box>
            <SearchForm onSubmit={handleSearch} isLoading={isLoading} />
            {error && (
                <Alert severity="error" sx={{ mt: 2 }}>
                    {error}
                </Alert>
            )}
        </Box>
    );
};

export default HomePage;
