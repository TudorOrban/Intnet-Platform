"""Create opf_models table

Revision ID: c6dac86e40b9
Revises: 
Create Date: 2025-03-14 13:29:49.140413

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'c6dac86e40b9'
down_revision: Union[str, None] = None
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    op.create_table(
        'opf_models',
        sa.Column('id', sa.Integer(), primary_key=True, index=True),
        sa.Column('name', sa.String(), nullable=False),
        sa.Column('created_at', sa.DateTime(timezone=True), server_default=sa.func.now()),
        sa.Column('storage_path', sa.String()),
        sa.Column('description', sa.String()),
        sa.Column('version', sa.String())
    )


def downgrade() -> None:
    op.drop_table('opf_models')
