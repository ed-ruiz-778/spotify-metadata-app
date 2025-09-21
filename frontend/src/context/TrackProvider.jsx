import { useState } from 'react';
import { TrackContext } from './TrackContext';

const TrackProvider = ({ children }) => {
    const [trackData, setTrackData] = useState(null);
    const [coverUrl, setCoverUrl] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const value = {
        trackData,
        setTrackData,
        coverUrl,
        setCoverUrl,
        isLoading,
        setIsLoading,
        error,
        setError,
    };

    return <TrackContext.Provider value={value}>{children}</TrackContext.Provider>;
};

export default TrackProvider;
