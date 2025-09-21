import { Routes, Route } from 'react-router-dom';
import { Container, Typography, Box } from '@mui/material';
import HomePage from './pages/HomePage';
import TrackPage from './pages/TrackPage';

function App() {
    return (
        <Container maxWidth="md">
            <Box sx={{ my: 4, textAlign: 'center' }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Spotify Track Metadata Finder
                </Typography>
            </Box>

            <main>
                <Routes>
                    <Route path="/" element={<HomePage />} />

                    <Route path="/track/:isrc" element={<TrackPage />} />
                </Routes>
            </main>
        </Container>
    );
}

export default App;
