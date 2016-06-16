public class ClipPathFinder extends PDFGraphicsStreamEngine implements Iterable<Path>
{
    public ClipPathFinder(PDPage page)
    {
        super(page);
    }

    //
    // PDFGraphicsStreamEngine overrides
    //
    public void findClipPaths() throws IOException
    {
        processPage(getPage());
    }

    @Override
    public void appendRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) throws IOException
    {
        startPathIfNecessary();
        currentPath.appendRectangle(toFloat(p0), toFloat(p1), toFloat(p2), toFloat(p3));
    }

    @Override
    public void drawImage(PDImage pdImage) throws IOException { }

    @Override
    public void clip(int windingRule) throws IOException
    {
        currentPath.complete(windingRule);
        paths.add(currentPath);
        currentPath = null;
    }

    @Override
    public void moveTo(float x, float y) throws IOException
    {
        startPathIfNecessary();
        currentPath.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) throws IOException
    {
        currentPath.lineTo(x, y);
    }

    @Override
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) throws IOException
    {
        currentPath.curveTo(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public Point2D.Float getCurrentPoint() throws IOException
    {
        return currentPath.getCurrentPoint();
    }

    @Override
    public void closePath() throws IOException
    {
        currentPath.closePath();
    }

    @Override
    public void endPath() throws IOException
    {
        currentPath = null;
    }

    @Override
    public void strokePath() throws IOException
    {
        currentPath = null;
    }

    @Override
    public void fillPath(int windingRule) throws IOException
    {
        currentPath = null;
    }

    @Override
    public void fillAndStrokePath(int windingRule) throws IOException
    {
        currentPath = null;
    }

    @Override
    public void shadingFill(COSName shadingName) throws IOException
    {
        currentPath = null;
    }

    void startPathIfNecessary()
    {
        if (currentPath == null)
            currentPath = new Path();
    }

    Point2D.Float toFloat(Point2D p)
    {
        if (p == null || (p instanceof Point2D.Float))
        {
            return (Point2D.Float)p;
        }
        return new Point2D.Float((float)p.getX(), (float)p.getY());
    }

    //
    // Iterable<Path> implementation
    //
    public Iterator<Path> iterator()
    {
        return paths.iterator();
    }

    Path currentPath = null;
    final List<Path> paths = new ArrayList<Path>();
}