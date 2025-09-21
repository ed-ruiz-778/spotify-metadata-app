import { useContext } from 'react';
import { TrackContext } from '../context/TrackContext';

export const useTrack = () => {
    const context = useContext(TrackContext);
    if (!context) {
        throw new Error('useTrack must be used within a TrackProvider');
    }
    return context;
};
