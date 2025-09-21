import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getTrackMetadata, getCoverImage } from '../services/apiService';
import { Card, CardContent, CardMedia, Typography, Box, CircularProgress, Alert, Button } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const TrackPage = () => {
    const { isrc } = useParams();

    const [localTrack, setLocalTrack] = useState(null);
    const [localCoverUrl, setLocalCoverUrl] = useState(null);
    const [localIsLoading, setLocalIsLoading] = useState(true);
    const [localError, setLocalError] = useState(null);

    useEffect(() => {
        const fetchMetadata = async () => {
            setLocalIsLoading(true);
            setLocalError(null);
            setLocalCoverUrl(null);

            try {
                const metadataResponse = await getTrackMetadata(isrc);
                setLocalTrack(metadataResponse.data);
            } catch (err) {
                const errorMessage = err.response?.data?.message || 'Failed to fetch track details.';
                setLocalError(errorMessage);
            } finally {
                setLocalIsLoading(false);
            }
        };

        fetchMetadata();
    }, [isrc]);

    useEffect(() => {
        if (!localTrack || localCoverUrl) return;

        let imageUrl;

        const fetchCover = async () => {
            try {
                const imageResponse = await getCoverImage(localTrack.isrc);
                imageUrl = URL.createObjectURL(imageResponse.data);
                setLocalCoverUrl(imageUrl);
            } catch (err) {
                console.error("Error fetching cover image:", err);
                setLocalError('Failed to load cover image.');
            }
        };

        fetchCover();

        return () => {
            if (imageUrl) {
                URL.revokeObjectURL(imageUrl);
            }
        };
    }, [localTrack, localCoverUrl]);

    return (
        <Box>
            <Button component={Link} to="/" startIcon={<ArrowBackIcon />} sx={{ mb: 2 }}>
                Search Another Track
            </Button>

            {localIsLoading && <Box sx={{ display: 'flex', justifyContent: 'center' }}><CircularProgress /></Box>}
            
            {localError && <Alert severity="error">{localError}</Alert>}
            
            {localTrack && localCoverUrl && !localIsLoading && (
                <Card sx={{ display: 'flex', flexDirection: { xs: 'column', md: 'row' } }}>
                    <CardMedia
                        component="img"
                        sx={{ width: { xs: '100%', md: 250 }, height: { xs: 'auto', md: 250 } }}
                        image={localCoverUrl}
                        alt={`Cover for ${localTrack.albumName}`}
                    />
                    <CardContent sx={{ flex: 1 }}>
                        <Typography component="div" variant="h5">{localTrack.name}</Typography>
                        <Typography variant="subtitle1" color="text.secondary" component="div">{localTrack.artistName}</Typography>
                        <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                            <strong>Album:</strong> {localTrack.albumName}<br />
                            <strong>Explicit:</strong> {localTrack.isExplicit ? 'Yes' : 'No'}<br />
                            <strong>Duration:</strong> {localTrack.playbackSeconds} seconds
                        </Typography>
                    </CardContent>
                </Card>
            )}
        </Box>
    );
};

export default TrackPage;
