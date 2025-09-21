import { useState } from 'react';
import { TextField, Button, Box, CircularProgress } from '@mui/material';

const SearchForm = ({ onSubmit, isLoading }) => {
    const [isrc, setIsrc] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        if (isrc.trim()) {
            onSubmit(isrc.trim());
        }
    };

    return (
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField
                margin="normal"
                required
                fullWidth
                id="isrc"
                label="ISRC Code"
                name="isrc"
                autoFocus
                value={isrc}
                onChange={(e) => setIsrc(e.target.value)}
                disabled={isLoading}
            />
            <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2, position: 'relative' }}
                disabled={isLoading}
            >
                Fetch Track
                {isLoading && (
                    <CircularProgress
                        size={24}
                        sx={{
                            position: 'absolute',
                            top: '50%',
                            left: '50%',
                            marginTop: '-12px',
                            marginLeft: '-12px',
                        }}
                    />
                )}
            </Button>
        </Box>
    );
};

export default SearchForm;
